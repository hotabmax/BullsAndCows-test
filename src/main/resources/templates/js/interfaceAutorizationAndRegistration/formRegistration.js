import {useForm} from "react-hook-form";
import {Button, TextField, FormControl} from "@mui/material";
import React,{useState} from "react";
import axios from "axios";

export function FormRegistration(){
    const {register, handleSubmit, formState: { errors }} = useForm()
    const [failAutorization, setFailAutorization] = useState("")
    function submit(data){
        console.log(data)
        axios.post('http://localhost:8080/tryRegistration', data)
            .then(res =>{
                if(res.data === "Успех"){
                    setFailAutorization()
                    axios.post('http://localhost:8080/registration', data)
                        .then(res =>{
                            setTimeout(() =>{
                                window.location.reload()
                            },100)
                        }
                        )
                } else setFailAutorization("Логин занят")
            })
    }
    return <form onSubmit={handleSubmit(submit)}>
        <p>{failAutorization}</p>
        <TextField
            label="Введите логин"
            variant="outlined"
            style={{margin: '5px'}}
            {...register( "login")}
        />
        <TextField
            label="Введите пароль"
            variant="outlined"
            style={{margin: '5px'}}
            {...register( "password")}
        />
        <Button
            variant="outlined"
            type="submit"
            style={{marginLeft: '50px', marginTop: '5px'}}
        >Регистрация</Button>
    </form>;
}