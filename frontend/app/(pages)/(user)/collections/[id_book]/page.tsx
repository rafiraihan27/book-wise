'use client'
import { useParams } from "next/navigation"

export default function DetailBook(){
    const params = useParams();
    const { id_book } = params;
    return(
        <div>
            Detail book. ID: {id_book}
        </div>
    )
}