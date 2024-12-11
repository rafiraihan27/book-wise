import { NextResponse } from 'next/server';
import { transactions } from '@/app/api/transactions/data';
import { newTransaction, Transaction } from '@/types/interfaces';

export async function GET(req: Request) {
    const url = new URL(req.url);
    const search = url.searchParams.get('search') || "";
    const status = url.searchParams.get('status') || "";
    const type = url.searchParams.get('type') || "";
    const userId = url.searchParams.get('userId') || "";

    const filteredTransactions = transactions.filter((transaction: Transaction) => {
        const matchesSearch =
            search === "all" ||
            !search ||
            transaction.invoiceCode.toLowerCase().includes(search.toLowerCase()) ||
            transaction.user.name.toLowerCase().includes(search.toLowerCase());

        const matchesStatus =
            status === "all" ||
            !status ||
            transaction.status.toLowerCase() === status.toLowerCase();

        const matchesType =
            type === "all" ||
            !type ||
            transaction.type.toLowerCase() === type.toLowerCase();

        const matchesUser =
            userId === "all" ||
            !userId ||
            transaction.user.id.toLowerCase() === userId.toLowerCase();

        return matchesSearch && matchesStatus && matchesType && matchesUser;
    });

    return NextResponse.json(filteredTransactions);
}

let currentInvoiceNumber = 1; // Simpan ini di database untuk persisten

function generateInvoiceCodeSequential() {
    const prefix = 'INV-';
    const datePart = new Date().toISOString().slice(0, 10).replace(/-/g, '');
    const invoiceNumber = String(currentInvoiceNumber).padStart(4, '0'); // Tambahkan 0 di depan jika perlu
    
    currentInvoiceNumber++; // Increment setelah kode dibuat
    return `${prefix}${datePart}-${invoiceNumber}`;
}

export async function POST(req: Request) {    
    try {
        const body: newTransaction = await req.json(); // Parse body dari request
        // Validasi field wajib
        const requiredFields: (keyof newTransaction)[] = [
            'userId',
            'totalFee',
            'paymentMethod',
            'paymentEvidence',
            'items',
            'dateFrom',
            'dateTo',
        ];

        const missingFields = requiredFields.filter((field) => body[field] == null || body[field] === "");
        if (missingFields.length > 0) {
            return NextResponse.json(
                { error: `Field(s) ${missingFields.join(', ')} are required.` },
                { status: 400 }
            );
        }

        // Membuat transaksi baru berdasarkan interface
        const newTransactions: Transaction = {
            id: crypto.randomUUID(),
            invoiceCode: generateInvoiceCodeSequential(),
            dateRange: {
                from: body.dateFrom,
                to: body.dateTo
            },
            status: 'pending',
            type: 'borrow',
            user: {
                id: body.userId,
                email: '',
                name: '',
                phone: '',
                role: ''
            },
            totalFee: body.totalFee,
            paymentMethod: body.paymentMethod,
            paymentEvidence: body.paymentEvidence,
            items: body.items
        };

        // Menambahkan buku baru ke koleksi
        transactions.push(newTransactions);

        return NextResponse.json(newTransactions, { status: 201 });
    } catch (error) {
        console.error('Error handling POST request:', error);
        return NextResponse.json(
            { error: 'Failed to add the book. Please try again.' },
            { status: 500 }
        );
    }
}