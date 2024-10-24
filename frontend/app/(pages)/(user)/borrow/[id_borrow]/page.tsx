'use client'
import { useParams } from "next/navigation"

export default function DetailBorrow(){
    const params = useParams()
    const { id_borrow } = params;
    return(
        <div>Detail borrow page. ID: {id_borrow}</div>
    )
}