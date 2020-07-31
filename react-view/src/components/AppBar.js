import React from "react";
import {Box, Button, Heading, Menu, Text} from 'grommet';
import {Down, List, Organization, Radial} from "grommet-icons";
import {Sun} from "react-feather";

export const AppBar = props => (
  <Box
    tag = 'header'
    direction='row'
    align='center'
    background= 'transp10'
    pad={{ left: 'medium', right: 'small', vertical: 'none' }}

    {...props}
  />
);

export class MainBar extends React.Component {
    constructor(props) {
        super(props);
        this.setThemeName = () => {
            this.props.setThemeName(!this.props.dmActivo ? "darkTheme" : "theme");
        };
    }

    render() {
        return (<AppBar>
                <Box direction={"row"} margin={"none"}>
                    <Button
                        // TODO onClick =  this.irPaginaPrincipal
                    >
                        {/*Logo*/}
                        <Organization color={'orange'} style={{verticalAlign: "middle"}}/>
                        <Text weight={"bold"} style={{verticalAlign: "middle"}}>PUCPganizer</Text>
                    </Button>
                    {/*Cambiar color Mode*/}
                    <Button
                        focusIndicator={false} margin={{left: "small"}}
                        icon={<Sun color={this.props.dmActivo ? 'orange' : 'grey'}/>}
                        onClick={this.setThemeName}
                        hoverIndicator={{
                            color: "btnHover", opacity: "1"
                        }}
                        style={{borderRadius: '50%'}}
                    />
                </Box>
                <Box
                    flex direction='row' align={"center"} justify={"center"}>
                    {/*Items del menu*/}
                    <Button style={{padding:"7px"}}>
                        idk something maybe</Button>
                    <Button style={{padding:"7px"}}>
                    about</Button>
                    <Button style={{ padding:"7px"}}>
                        sugerencias</Button>
                </Box>

                <Box direction={"row"} pad={"none"}>
                    {/*Cambiar color, ver cursos y Organizar*/}
                    <Button style={{fontWeight: "bold"}}>ver cursos</Button>
                    <Button margin={"small"} label={"Organizar →"} size={"small"}
                            style={{ border:"none",
                                background: '#E95656', borderRadius: '32px',
                                color: "#FEFBF8", padding: "5px 15px 5px 15px",
                                fontWeight: "bold"
                            }}
                    />
                </Box>
            </AppBar>
        );
    }
}