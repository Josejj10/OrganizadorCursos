import React from "react";
import {
    Box,
    Text,
    DataTable,
    TextInput, Button
} from "grommet";
import axios from 'axios';
import {DetalleCurso} from "./DetalleCurso";
import {CursosListURL} from "../URLs";
import {Download, Search} from "grommet-icons";
import { CSVLink, CSVDownload } from "react-csv";



async function CursosDataService() {
    let res = await axios.get(`${CursosListURL}`);
    let data = res.data;
    return data._embedded.cursoListaDTOList.map(
        curso=>{
            curso.nombre = curso.nombre? curso.nombre : "Por determinar";
            curso.ciclo = curso.ciclo !== 20? curso.ciclo : "Electivo";
            return curso;
        }
    );
}



async function CursosFullDataService() {
    let res = await axios.get(`${CursosListURL}/fullData`);
    let data = res.data;
    return data._embedded.cursoDTOList.map(
        curso=>{
            curso.nombre = curso.nombre? curso.nombre : "Por determinar";
            curso.ciclo = curso.ciclo !== 20? curso.ciclo : "Electivo";
            return curso;
        }
    );
}



export class ListCursos extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            cursos: [],
            showDetalle: false,
            linkActual: "",
            id: -1,
            buscado: "",
            cursosSv: [],
            cursosB: [],
            csvData: [],
        }

        this.loadDetalle = this.loadDetalle.bind(this);
        this.loadOtroCurso = this.loadOtroCurso.bind(this);
        this.closeDetalle = this.closeDetalle.bind(this);
        this.searchCursos = this.searchCursos.bind(this);
    }

    searchCursos(bus) {
        this.setState({buscado: bus})

        let cur = this.state.cursosSv.filter((c) => {
            if (c.code.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(bus.toLowerCase())
                || c.nombre.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(bus.toLowerCase()))
                return c;
        })
        this.setState({cursosB: cur});
        if(!cur.length) return;
        this.setState({cursos: cur});
    }

    loadDetalle(event, datum) {
        event.preventDefault();
        this.setState({showDetalle: true, linkActual: datum._links.self.href, id: datum.id})
    }

    loadOtroCurso = (link, id) => {
        console.log(link)
        console.log(id)
        this.setState({showDetalle: false}, () => {
            setTimeout(() => {
                this.setState({showDetalle: true, linkActual: link, id: id});
            }, 300);
        });
    }

    closeDetalle() {
        this.setState({showDetalle: false})
        console.log("closed")
    }

    renderCurso(datum) {
        if (!datum.code) return; // Para las columnas grouped by
        return (
            <Text
                pad={"small"}
                onClick={(evt) => {
                    this.loadDetalle(evt, datum)
                }}
                focusIndicator={false}
                primary={true}
                color={"principal"}
                weight={"normal"}
                hoverIndicator={{color: 'dark-3', size: "cover"}}
                size={"medium"}
                style={{cursor: "pointer", textDecoration: "underline"}}
            >
                {datum.nombre}
            </Text>
        );
    }

    groupColumns = [
        {
            property: 'ciclo',
            header: 'Ciclo',
            align: 'start',
            sortable: true,
        },
        {
            property: 'code',
            align: 'start',
            header: <Text>Codigo</Text>,
            sortable: true,
        },
        {
            property: 'nombre',
            header: 'Nombre',
            align: 'start',
            sortable: true,
            render: datum => this.renderCurso(datum),
        },
    ];

    calcularData(){
        let cursos = this.state.cursos
        let data = [];
        for(let i = 0; i<cursos.length; i++){
            data.push([cursos[i].nombre, cursos[i].code, cursos[i].ciclo])
        }
        // Buscar los links para cada curso


        this.setState({csvData: data});
    }



    componentDidMount() {
        CursosDataService().then(r => this.setState({cursos: r, cursosSv: r,cursosB:r}))
    }

    render() {
        return (
            <Box
                fill
                background='back'
                flex
                align='center'
                justify='center'
                pad='large'
            >
                {(this.state.showDetalle) &&
                <DetalleCurso
                    link={this.state.linkActual}
                    close={this.closeDetalle}
                    openNew={this.loadOtroCurso}
                    key={this.id}
                />}
                <Box
                    round={{size: "small"}}
                    overflow='auto'
                    background='frontBackground'
                    // margin='small'
                    pad={"small"}
                    // fill={"horizontal"}
                    align='center'
                    // appCentered = 'true'
                    elevation={'large'}
                >
                    {this.state.cursosSv.length ?
                        <Box pad={"small"}>
                            <Box margin={"small"} fill >
                                <TextInput
                                    placeholder={"Buscar"}
                                    icon={<Search/>}
                                    size={"medium"}
                                    value={this.state.buscado}
                                    onChange={event => this.searchCursos(event.target.value)}
                                />
                                {!this.state.cursosB.length?
                                    <Box round={{size: "small"}} pad = {"xxsmall"}
                                        margin={"small"} background={"light-red"} alignContent={"center"}
                                         height={{max:"xsmall"}} overflow={"hidden"}
                                    >
                                        <Text textAlign={"center"} margin={"none"}  wordBreak={"break-all"}>
                                            No se hallaron resultados para {this.state.buscado}</Text>
                                    </Box>
                                    :""
                                }
                            </Box>
                                <DataTable
                                    background={{
                                        header: 'strong',
                                        body: 'brand'
                                    }}
                                    border="bottom"
                                    size="medium"
                                    columns={this.groupColumns}
                                    data={this.state.cursos}
                                    sortable
                                    primaryKey='id'
                                    groupBy='ciclo'
                                    sort={{direction: "asc", property: 'ciclo'}}
                                />
                                <Box margin={"medium"}>
                                    <CSVLink data={this.state.csvData}
                                             onClick={()=>this.calcularData()}>
                                        Exportar Cursos Visibles
                                    </CSVLink>

                                </Box>
                        </Box>
                        :
                        <Text textAlign={"center"} size='small'>Loading...</Text>
                    }
                </Box>
            </Box>
        );
    }
}