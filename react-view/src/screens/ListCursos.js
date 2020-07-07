import React from "react";
import {Box, Text, DataTable, Table, TableBody, TableCell, TableHeader, TableRow} from "grommet";
import axios from 'axios';

const CursosApiURL = 'http://localhost:8080';
const CursosListURL = `${CursosApiURL}/cursos`

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

const columns = [
    {
        property: 'ciclo',
        header: 'Ciclo',
    },
    {
        property: 'code',
        header: <Text>Codigo</Text>,
    },
    {
        property: 'nombre',
        header: 'Nombre'
    },
];

const groupColumns = [...columns];

export class ListCursos extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cursos: [],
        }

    }

    componentDidMount() {
        CursosDataService().then(r=> this.setState({cursos:r}))
    }

    render() {
        if (this.state.cursos.length){
            return(
                <DataTable
                    columns={groupColumns}
                    data={this.state.cursos}
                    sortable
                    primaryKey='id'
                    groupBy='ciclo'
                    sort='ciclo'
                />
            );
        }
        return <Text size='small'>Loading...</Text>
    }
}


export class ListCursosD extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cursos: [],
        }
        this.refreshCursos = this.refreshCursos.bind(this);
    }

    componentDidMount() {
        this.refreshCursos();
    }

    refreshCursos() {
        CursosDataService.listCursos().then(
            response => {
                this.setState({cursos: response.data._embedded.cursoListaDTOList})
            }
        )
    }

    render() {
        return (
            <Box
                fill
                background='back'
                flex
                align='center'
                justify='center'
                pad = 'large'
            >
                <Box
                    // height='medium'
                    overflow='auto'
                >
                    <Table
                    >
                        <TableHeader>
                            <TableRow>
                                <TableCell scope='col' border='bottom'>
                                    Ciclo
                                </TableCell>
                                <TableCell scope='col' border='bottom'>
                                    Codigo
                                </TableCell>
                                <TableCell scope='col' border='bottom'>
                                    Nombre
                                </TableCell>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {
                                this.state.cursos.sort(function compareNumbers(a, b) {
                                    return a.ciclo - b.ciclo;
                                }).map(
                                    curso =>
                                        <TableRow key={curso.id}>
                                            <TableCell scope='col' border='bottom'>
                                                {curso.ciclo === 20 ? 'Electivo' : curso.ciclo}
                                            </TableCell>
                                            <TableCell scope='col' border='bottom'>
                                                <strong>{curso.code}</strong>
                                            </TableCell>
                                            <TableCell scope='col' border='bottom'>
                                                {curso.nombre ? curso.nombre : 'Aun no Determinado'}
                                            </TableCell>
                                        </TableRow>
                                )
                            }
                        </TableBody>
                    </Table>
                </Box>
            </Box>
        );
    }

}