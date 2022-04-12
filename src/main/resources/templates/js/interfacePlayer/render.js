import React, {useState} from "react";
import { createRoot } from 'react-dom/client';
import {FormPlay} from "./formPlay";
import {TableHistory} from "./tableHistory";
import {TableProgress} from "./tableProgress";
import axios from "axios";
import {Button} from "@mui/material";
import {ButtonExit} from "./buttonExit";

export const AlertContext = React.createContext()

const container = document.getElementById('root');
const root = createRoot(container);

function Game(){

    const [tableHistoryData, setTableHistoryData] = useState([{value:'-', bulls: '-', cows: '-'}])
    const [tableProgressData, setTableProgressData] = useState([{login:'-', middleattempts: '-'}])
    let seterFunctions = {}
    seterFunctions.setTableHistoryData = setTableHistoryData
    seterFunctions.setTableProgressData = setTableProgressData
    return <AlertContext.Provider value={{tableHistoryData, tableProgressData}}>
        <TableHistory/>
        <TableProgress/>
        <ButtonExit/>
        <FormPlay
            seterFunctions={seterFunctions}
        />
    </AlertContext.Provider>;
}

root.render(<Game/>)
