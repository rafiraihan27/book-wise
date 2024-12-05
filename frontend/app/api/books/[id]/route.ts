import { NextResponse } from 'next/server';
import { books } from '../data';

export async function GET(req: Request, { params }: { params: { id: string } }) {
    const { id } = params;

    const book = books.find((book) => book.id === id);

    if (book) {
        return NextResponse.json(book);
    } else {
        return NextResponse.json({ error: 'Book not found' }, { status: 404 });
    }
}