import React,{useContext} from "react";
import {Table, TableHead, TableBody, TableRow, TableCell, TableContainer, Paper, InputLabel} from "@mui/material";
import {AlertContext} from "./render";


export function TableProgress(){

    const {tableProgressData} = useContext(AlertContext)

    return <TableContainer sx={{
        maxWidth: 500,
        maxHeight: 250,
        display: 'inline-block',
        marginLeft: 5
    }} component={Paper}>
        <p style={{marginLeft: '150px'}}>Рекорды</p>
        <Table stickyHeader aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell>Позиция</TableCell>
                    <TableCell>Игрок</TableCell>
                    <TableCell>Среднее количество попыток до победы</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {tableProgressData.map((data, index) => (
                    <TableRow>
                        <TableCell>{++index}</TableCell>
                        <TableCell>{data.login}</TableCell>
                        <TableCell>{data.middleattempts}</TableCell>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    </TableContainer>;
}