import React, {useState} from 'react';
import THEMES from './themes/grommetThemes';
import Helmet from 'react-helmet';
import {MainBar} from './components/AppBar.js';
import {SidebarA} from "./components/SideBar";
import {ListCursos} from "./screens/ListCursos";
import {OverviewCiclos} from "./screens/OverviewCiclos";
import {
    Box,
    Grommet,
    ResponsiveContext
} from 'grommet';

function App() {
    const [themeName, setTName] = useState('theme');
    const [showSideBar, setShowSideBar] = useState(false);
    const [dmActivo, setDmActivo] = useState(false);

    function setThemeName(str) {
        setTName(str);
        setDmActivo(str.includes("dark"));
    }

    return (
        <Grommet theme={THEMES[themeName || 'theme']} full>
            <Helmet titleTemplate="%s - Organizador" defaultTitle="Cursos">
                <meta name="description" content="Documentacion"/>
                <meta
                    name="keywords"
                    content="Cursos, Requisitos, Organizador"
                />
            </Helmet>
            <ResponsiveContext.Consumer>
                {size => (
                    <Box>
                        <MainBar setShowSideBar={setShowSideBar} showSideBar={showSideBar}
                                 setThemeName={setThemeName} dmActivo={dmActivo}/>
                        <Box
                            direction='row'
                            flex
                            overflow={{horizontal: 'hidden'}}
                            fill
                            background='back'
                        >
                            <Box fill flex>
                                <ListCursos/>
                            </Box>
                            <SidebarA showSideBar={showSideBar} setShowSideBar={setShowSideBar}
                                      setThemeName={setThemeName} dmActivo={dmActivo} size={size}/>
                        </Box>
                    </Box>
                )}
            </ResponsiveContext.Consumer>
        </Grommet>
    );
}

export default App;
