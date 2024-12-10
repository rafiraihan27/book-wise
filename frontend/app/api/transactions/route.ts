import { NextResponse } from 'next/server';
import { transactions } from '@/app/api/transactions/data';
import { Transaction } from '@/types/interfaces';

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
