import React from "react";
import {Box, Button, Heading} from 'grommet';
import { List} from "grommet-icons";
import {Sun} from "react-feather";

export const AppBar = props => (
  <Box
    tag = 'header'
    direction='row'
    align='center'
    justify='between'
    background= 'brand'
    pad={{ left: 'medium', right: 'small', vertical: 'small' }}
    elevation= {props.dmActivo? 'none' :'medium'}
    style={{ zIndex: '1'
      }}
    {...props}
  />
);

export class MainBar extends React.Component {
    constructor(props) {
        super(props);
        this.setShowSideBar = () => {
            this.props.setShowSideBar(!this.props.showSideBar);
        }
        this.setThemeName = () => {
            this.props.setThemeName(!this.props.dmActivo ? "darkTheme" : "theme");
        };
    }

    render() {
        return (<AppBar dmActivo = {this.props.dmActivo}>
                <Button
                    focusIndicator={false}
                    icon={<Sun color={this.props.dmActivo ? 'orange' : 'black'}/>}
                    onClick={this.setThemeName}
                    hoverIndicator={{
                        color: "btnHover", opacity: "1"
                    }}
                    style={{borderRadius: '50%'}}
                />
                <Heading textAlign='center' level='2' margin='none'>Cursos Ing. Informática</Heading>
                <Button
                    focusIndicator={false}
                    icon={<List/>}
                    onClick={this.setShowSideBar}
                    hoverIndicator={{
                        color: "btnHover", opacity: "1"
                    }}
                    style={{borderRadius: '50%'}}
                />
            </AppBar>
        );
    }
}