import React, {useState} from "react";
import {Box, Tab} from "@mui/material"
import {TabPanel, TabContext, TabList} from "@mui/lab";
import {FormAutorization} from "./formAutorization";
import {FormRegistration} from "./formRegistration";


export function CustomTabs(){
    const [value, setValue] = useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    return <Box sx={{margin: 'auto', width: '300px', typography: 'body1' }}>
        <TabContext value={value}>
            <Box>
                <TabList onChange={handleChange} aria-label="lab API tabs example">
                    <Tab label="Авторизация" value="1" />
                    <Tab label="Регистрация" value="2" />
                </TabList>
            </Box>
            <TabPanel value="1"><FormAutorization/></TabPanel>
            <TabPanel value="2"><FormRegistration/></TabPanel>
        </TabContext>
    </Box>;
}