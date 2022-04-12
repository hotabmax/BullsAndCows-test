import React,{useContext} from "react";
import {Table, TableHead, TableBody, TableRow, TableCell, TableContainer, Paper} from "@mui/material";
import {AlertContext} from "./render";

export function TableHistory(){

    const {tableHistoryData} = useContext(AlertContext)
    return <TableContainer
        sx={{
            maxWidth: 250,
            maxHeight: 250,
            display: 'inline-block',
            marginLeft: 40
    }} component={Paper}>
        <p style={{marginLeft: '75px'}}>История</p>
        <Table stickyHeader aria-label="simple table">
            <TableHead>
                <TableRow>
                    <TableCell>Попытка</TableCell>
                    <TableCell>Результат</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {tableHistoryData.map((data) => (
                    <TableRow>
                        <TableCell>{data.value}</TableCell>
                        <TableCell>{data.bulls}Б{data.cows}К</TableCell>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    </TableContainer>;
}