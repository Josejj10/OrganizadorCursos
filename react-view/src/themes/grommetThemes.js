import { hpe } from 'grommet-theme-hpe';

const theme = {
    global: {
        colors: {
            'brand': '#f8f8f8',
            'sunny': '#FFD602',
            'btnHover' : '#ededed',
            'secondary':'#f5f5f5',
            'back' : '#f0f0f0',
        },
        font: {
            family: 'Lato',
            size: '14px',
            height: '20px',
        },
    },
};

const darkTheme = {
    global: {
        colors: {
            'brand': '#191919',
            'sunny': '#FFD602',
            'btnHover': '#333333',
            'secondary':'#2B2B2B',
            'back' : '#0f0f0f',
        },
        font: {
            family: 'Lato',
            size: '14px',
            height: '20px',
        },
    },
};

const THEMES = {
    theme,
    hpe,
    darkTheme
}


export default THEMES;