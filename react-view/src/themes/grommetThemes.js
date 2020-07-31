const regular = {
    global: {
        active:{
            background:{
                back:'black',
            }
        },
        colors: {
            'lowContrast':'#707070',
            'mediumContrast':'#3B3B3D',
            'highContrast':'#1D1D1E',
            'modal':'#EFE7E0',
            'modalError':'#E77878',
            'modalWarning':'#FADE8D',
            'back':'#FEFBF8',
            'error':'#E95656',
            'warning':'#F8D05A',
            'disabled':'#BFC7CB',
            'link':'#23A6DF',
            'b+2':'#286381',
            'b+1':'#40809F',
            'button':'#578CA6',
            'b-1':'#79A3B7',
            'b-2':'#96B7C7',

            'btnHover' : '#ededed',
            'transp10': 'rgba(0, 0, 0, 0)',
            'focus': 'rgba(0, 0, 0, 0)',
        },
        font: {
            family: 'Heebo',
            size: '26px',
            color: 'mediumContrast'
            //height: '20px',
        },
    },
    tab:{
        active:{color:"a"},
        border:{
            side:'bottom', size:'small',
            color:'b',
            hover:{
                color:'a',
            },
            active:{
                color:'a',
            }
        },
        color:"b",
        hover:{ color: "a"},
    },
    tabs:{
        header:{
            background:{
                 color:'a',
            }
        }
    }
};

const darkMode = {
    global: {
        colors: {
            'lowContrast':'#707070',
            'mediumContrast':'#3B3B3D',
            'highContrast':'#1D1D1E',
            'modal':'#EFE7E0',
            'modalError':'#E77878',
            'modalWarning':'#FADE8D',
            'background':'#FEFBF8',
            'error':'#E95656',
            'warning':'#F8D05A',
            'disabled':'#BFC7CB',
            'link':'#23A6DF',
            'b+2':'#286381',
            'b+1':'#40809F',
            'button':'#578CA6',
            'b-1':'#79A3B7',
            'b-2':'#96B7C7',

            'btnHover' : '#ededed',
            'focus': 'rgba(0, 0, 0, 0)',
        },
        font: {
            family: 'Heebo',
            size: '26px',
            //height: '20px',
        },
    },
    tab:{
        active:{color:"a"},
        border:{
            side:'bottom', size:'small',
            color:'b',
            hover:{
                color:'a',
            },
            active:{
                color:'a',
            }
        },
        color:"b",
        hover:{ color: "a"},
    },
    tabs:{
        header:{
            background:{
                color:'a',
            }
        }
    }
};

const THEMES = {
    regular,
    darkMode
}


export default THEMES;