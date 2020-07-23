import React from "react";
import {Anchor, Box, Button, Heading, Layer, Tab, Tabs, Text} from "grommet";
import axios from 'axios';
import {FormClose} from "grommet-icons";

// Extraer curso del URL


export class DetalleCurso extends React.Component {
    constructor(props) {
        super(props);
        this.closeDetalle = () => this.props.close();
        this.state = {
            curso: null,
            link: this.props.link,
        }
    }

    componentDidMount() {
        this.buscarCurso().then(r => this.setState({curso: r}))
    }


    async buscarCurso() {
        let res = await axios.get(`${this.state.link}`);
        return res.data;
    }

    render() {
        if (!this.state.curso) {
            return <Layer onClickOutside={this.closeDetalle}
                          onEsc={this.closeDetalle}
                          pad="large"
                          margin={"medium"}
                          full={false}

            ><Text margin={"medium"}>Loading...</Text></Layer>;
        }
        return (
            <Layer
                full={false}
                margin={"medium"}
                onClickOutside={this.closeDetalle}
                onEsc={this.closeDetalle}
                pad="large"
                key={this.props.key}
            >
                <Box
                    background='principal'
                    tag='header'
                    align='center'
                    justify='end'
                    direction='row'
                >
                    <Button
                        icon={<FormClose/>}
                        onClick={this.closeDetalle}
                        focusIndicator={false}
                        hoverIndicator={{
                            color: "btnHover", opacity: "1"
                        }}
                        style={{borderRadius: '50%'}}
                    />
                </Box>
                <Box    
                    background='brand'
                    align='center'
                >
                    <Heading
                        margin={"small"}
                        level={"3"}
                        textAlign={"center"}
                    >
                        {this.state.curso.code + " - " +(this.state.curso.nombre?this.state.curso.nombre:"Por determinar")}
                    </Heading>
                </Box>
                <TabsReqs
                    minCreditos = {this.state.curso.minCreditos}
                    requisitos = {this.state.curso.requisitos}
                    requeridoPor={this.state.curso.requeridoPor}
                    openNew = {this.props.openNew}
                />
            </Layer>
        );
    }
}

class TabsReqs extends React.Component {
    constructor(props) {
        super(props);
        this.requisitos = this.props.requisitos._embedded?this.props.requisitos._embedded.cursoRequisitoDTOList:[];
        this.requeridoPor = this.props.requeridoPor._embedded?this.props.requeridoPor._embedded.cursoRequisitoDTOList:[];
        this.minCreditos = this.props.minCreditos;

    }

    render() {
        if (!this.requisitos && !this.requisitos) return <Text>No es requisito ni es requerido por ningún curso.</Text>;
        return (
            <Box
                flex justify={"center"}
                background={"bBody"}
            >
                <Tabs>
                    <Tab title="Requisitos" key = {0}>
                        {(this.minCreditos === 0 && this.requisitos.length === 0) &&
                        <Box
                            justify={"center"}
                            align={"center"}
                            background={"sunny"}
                        >
                            <Text
                                textAlign={"center"}

                            >Este curso no tiene requisitos.</Text>
                        </Box>
                        }
                        {(this.minCreditos !== 0) &&
                        <Box
                            justify={"center"}
                            align={"center"}
                            background={"sunny"}
                        >
                            <Text
                                textAlign={"center"}
                            >Haber llevado {this.minCreditos} créditos.</Text>
                        </Box>}
                        <ListaReqs key={1} requisito={true} reqs={this.requisitos} openNew = {this.props.openNew}/>
                    </Tab>
                    <Tab title="Requerido Por" key = {3}>
                        {this.requeridoPor.length ?
                            <ListaReqs key={4} requisito={false} reqs={this.requeridoPor} openNew = {this.props.openNew}/>
                            :
                            <Box
                                justify={"center"}
                                align={"center"}
                                background={"sunny"}
                            >
                                <Text
                                    textAlign={"center"}
                                >Este curso no es requerido por ningun otro.</Text>
                            </Box>
                        }
                    </Tab>
                </Tabs>
            </Box>
        );
    }
}

class ListaReqs extends React.Component{
    constructor(props) {
        super(props);
        this.reqs = this.props.reqs;
        this.requisito = this.props.requisito;
    }

    nuevoDetalle = (c) =>{
        let link  = this.requisito?c._links.requeridoPor.href:c._links.requiere.href;
        let id  = this.requisito?c.id.requerido:c.id.requiere;
        this.props.openNew(link, id);
    }

    render() {
        let separateElements=[];
        for(var i=0; i< this.reqs.length;i+=4){
            var oneRow = [];
            oneRow.push(this.reqs.slice(i,i+4).map( c=>{
                let tipoReq = c.tipoRequisito===1? "(Llevar a la vez)":
                        c.tipoRequisito===2?
                        "(Nota mínima 08)":"";
                return (
                <Box
                    justify={"center"}
                    width={"small"}
                    margin={"medium"} align={"center"}
                    key={this.requisito?c.id.requerido:c.id.requiere}
                >
                    <Anchor
                        onClick={()=>this.nuevoDetalle(c)}
                        textAlign={"center"}
                    >
                        <strong>{this.requisito?c.codeRequerido:c.codeRequiere}</strong>
                    </Anchor>
                    <Text textAlign={"center"}>
                        {(this.requisito? c.nomRequerido : c.nomRequiere)}
                        {tipoReq && <br/>}
                        {tipoReq && tipoReq}
                    </Text>
                </Box>);
            }))
            separateElements.push(
                oneRow.map(cur=>
                    {return(
                        <Box fill flex justify={"center"}
                             align={"center"} direction={"row"}
                             key={cur.toString()}
                        >
                            {cur}
                        </Box>);
                    }
                )
            )
        }
        return separateElements;
    }
}