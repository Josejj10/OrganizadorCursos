import React from "react";
import {Box, Button, Collapsible, Heading, Layer} from "grommet";
import {FormClose } from "grommet-icons";
import { Sun } from 'react-feather';

export class SmallSidebar extends React.Component {
    constructor(props) {
        super(props);
        this.setThemeName = () => {
            this.props.setThemeName(!this.props.dmActivo ? "darkTheme" : "theme");
        };
    }

    render() {
        return (
            <Collapsible direction='horizontal' open={this.props.showSideBar}>
                <Box
                    flex
                    elevation= {this.props.dmActivo? 'none' :'medium'}
                    background='secondary'
                >
                    <Box
                        flex
                        width='small'
                        // background='secondary'
                        align='center'
                        justify='center'

                    >
                        <Heading>1</Heading>
                    </Box>
                    <Box
                        // background='secondary'
                        tag='footer'
                        align='center'
                        justify='end'
                        direction='row'

                    >
                        Dark Mode
                        <Button
                            icon={<Sun color={this.props.dmActivo ? 'orange' : 'black'}/>}
                            onClick={this.setThemeName}
                            focusIndicator={false}
                            hoverIndicator={{
                                color: "btnHover", opacity: "1"
                            }}
                            style={{borderRadius: '50%'}}
                        />
                    </Box>
                </Box>
            </Collapsible>
        );
    }
}

export class FullSidebar extends React.Component {
    constructor(props) {
        super(props);
        this.setShowSidebar = () => this.props.setShowSideBar(false);
        this.setThemeName = () => {
            this.props.setThemeName(!this.props.dmActivo ? "darkTheme" : "theme");
        };
    }

    render() {
        return (
            <Layer animation='none'>
                <Box overflow='hidden' flex>
                    <Box
                        background='brand'
                        tag='header'
                        align='center'
                        justify='end'
                        direction='row'
                    >
                        <Button
                            icon={<FormClose/>}
                            onClick={this.setShowSidebar}
                            focusIndicator={false}
                            hoverIndicator={{
                                color: "btnHover", opacity: "1"
                            }}
                            style={{borderRadius: '50%'}}
                        />
                    </Box>
                    <Box
                        fill
                        background='brand'
                        align='center'
                        justify='center'
                    >
                    </Box>
                    <Box
                        background='brand'
                        tag='footer'
                        align='center'
                        justify='end'
                        direction='row'
                    >
                        <Button
                            icon={<Sun color={this.props.dmActivo ? 'orange' : 'black'}/>}
                            onClick={this.setThemeName}
                            focusIndicator={false}
                            hoverIndicator={{
                                color: "btnHover", opacity: "1"
                            }}
                            style={{borderRadius: '50%'}}
                        />
                    </Box>
                </Box>
            </Layer>
        );
    }
}


export class SidebarA extends React.Component{
    constructor(props) {
        super(props);
        this.setShowSideBar = ()=>this.props.setShowSideBar(false);
        this.setThemeName = ()=> {
            this.props.setThemeName(!this.props.dmActivo?"darkTheme":"theme");
        };
    }

    render() {
        return (
            (!this.props.showSideBar || this.props.size !== 'small') ? (
                <SmallSidebar showSideBar={this.props.showSideBar} setShowSideBar={this.setShowSideBar}
                              setThemeName={this.setThemeName} dmActivo={this.props.dmActivo}
                />
            ) : (
                <FullSidebar
                    setShowSideBar={this.setShowSideBar}
                    setThemeName={this.setThemeName} dmActivo={this.props.dmActivo}/>
            )
        );
    }
}