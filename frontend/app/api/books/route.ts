import { NextResponse } from 'next/server'
import {books} from './data'

export async function GET(req: Request) {
    const url = new URL(req.url);
    const search = url.searchParams.get('search') || "";
    const category = url.searchParams.get('category') || "";
    const years = url.searchParams.get('years') || "";

    const filteredBooks = books.filter((book) => {
        const matchesSearch =
            search === "" ||
            book.title.toLowerCase().includes(search.toLowerCase()) ||
            book.author.toLowerCase().includes(search.toLowerCase());

        const matchesCategory =
            category === "" || book.category.toLowerCase() === category.toLowerCase();

        const matchesYear = years === "" || book.year.toString() === years;

        return matchesSearch && matchesCategory && matchesYear;
    });

    return NextResponse.json(filteredBooks);
}