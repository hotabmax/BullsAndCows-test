import React from "react";
import {Button} from "@mui/material";
import axios from "axios";

export function ButtonExit(){
    function click(){
        axios('http://localhost:8080/exit')
            .then(res =>{
                setTimeout(() =>{
                    window.location.reload()
                },100)
            }
            )
    }
    return <Button
        onClick={click}
        sx={{
            position: 'absolute'
        }}
    >
        Выход
    </Button>;
}