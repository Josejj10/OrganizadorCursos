import { hpe } from 'grommet-theme-hpe';

const theme = {
    global: {
        colors: {
            'brand': '#f8f8f8',
            'sunny': '#FFD602',
            'btnHover' : '#ededed',
            'secondary':'#f5f5f5',
            'back' : '#f0f0f0',
            'frontBackground' : '#ffffff',
            'strong' : '#000000',
            'focus': 'rgba(0, 0, 0, 0)',
            'principal':'#1db6c4',
            'bBody': '#AFAFAF',
            'tHeaders': '#DADADA',
            'light-red': 'rgba(255,105,97,0.52)',
        },
        font: {
            family: 'Lato',
            size: '14px',
            height: '20px',
        },
    },
    tab:{
        active:{color:"principal"},
        border:{
            side:'bottom', size:'small',
            color:'strong',
            hover:{
                color:'principal',
            },
            active:{
                color:'principal',
            }
        },
        color:"strong",
        hover:{ color: "principal"},
    },
    tabs:{
        header:{
            background:{
                 color:'tHeaders',
            }
        }
    }
};

const darkTheme = {
    global: {
        colors: {
            'brand': '#191919',
            'sunny': '#FFD602',
            'btnHover': '#333333',
            'secondary':'#2B2B2B',
            'back' : '#0f0f0f',
            'frontBackground' : '#000000',
            'strong' : '#ffffff',
            'border': '#ffd036',
            'focus': 'rgba(0, 0, 0, 0)',
        },
        font: {
            family: 'Lato',
            size: '14px',
            height: '20px',
        },
    },
    tab:{
        active:"#ffd036",
        color:"#ffffff",
        hover:{ color: "#ffd036"},

    },
    tabs:{
        header:{
            border:{
               // color:'#ffd036',
            }
        }
    }
};

const THEMES = {
    theme,
    hpe,
    darkTheme
}


export default THEMES;