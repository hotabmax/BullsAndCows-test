import {useForm} from "react-hook-form";
import {Button, FormHelperText, TextField} from "@mui/material";
import React, {useEffect} from "react";
import axios from "axios";



export function FormPlay({seterFunctions}){
    const {register, handleSubmit, formState: { errors }} = useForm()
    useEffect(()=>{
        if(document.readyState){
            axios.post('http://localhost:8080/findHistoryOfAttempts')
                .then(res => {
                    if(Array.isArray(res.data)){
                        seterFunctions.setTableHistoryData(res.data)
                    }
                    axios.post('http://localhost:8080/findProgress')
                        .then(res => {
                            if(Array.isArray(res.data)){
                                seterFunctions.setTableProgressData(res.data)
                            }
                        })
                })
        }
    })
    function submit(data){
        axios.post('http://localhost:8080/play', data)
            .then( res => {
                if (res.data === 'Успех'){
                    axios.post('http://localhost:8080/changeMiddleAttempts', data)
                        .then(res => {
                            if (res.data === 'Успех'){
                                setTimeout(() =>{
                                    axios.post('http://localhost:8080/findHistoryOfAttempts')
                                        .then(res => {
                                            if(Array.isArray(res.data)){
                                                seterFunctions.setTableHistoryData(res.data)
                                            }
                                            axios.post('http://localhost:8080/findProgress')
                                                .then(res => {
                                                    if(Array.isArray(res.data)){
                                                        seterFunctions.setTableProgressData(res.data)
                                                    }
                                                })
                                        })
                                },100)
                            }
                        })
                }
            }
            )
    }
    return <form
        onSubmit={handleSubmit(submit)}
        style={{marginLeft: 500, marginTop: 50}}
    >
        <TextField
            label="Введите число"
            variant="outlined"
            style={{margin: '5px'}}
            {...register( "value",{
                maxLength: {
                    value: 4,
                    message: "Введите 4-х значное число"
                },
                minLength: {
                    value: 4,
                    message: "Введите 4-х значное число"
                },
                pattern:{
                    value: new RegExp('[0-9]{4}'),
                    message: "Введите число"
                },
                validate: value =>
                    (parseInt(value / 1000) !== parseInt(value % 1000 / 100) &&
                        parseInt(value / 1000) !== parseInt(value % 100 / 10) &&
                        parseInt(value / 1000) !== parseInt(value % 10) &&
                        parseInt(value % 1000 / 100) !== parseInt(value / 1000) &&
                        parseInt(value % 1000 / 100) !== parseInt(value % 100 / 10) &&
                        parseInt(value % 1000 / 100) !== parseInt(value % 10) &&
                        parseInt(value % 100 / 10) !== parseInt(value / 1000) &&
                        parseInt(value % 100 / 10) !== parseInt(value % 1000 / 100) &&
                        parseInt(value % 100 / 10) !== parseInt(value % 10) &&
                        parseInt(value % 10)!== parseInt(value / 1000) &&
                        parseInt(value % 10)!== parseInt(value % 1000 / 100) &&
                        parseInt(value % 10)!== parseInt(value % 100 / 10)) || "Числа повторяются"
            })}
        />
        <FormHelperText
            id="my-helper-text"
            style={{marginLeft: '40px'}}
        >{errors.value && <p>{errors.value.message}</p>}</FormHelperText>
        <Button
            variant="outlined"
            type="submit"
            style={{marginLeft: '75px'}}
        >Играть</Button>
    </form>;
}