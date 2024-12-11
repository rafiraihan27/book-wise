import { newTransaction, Transaction } from "@/types/interfaces";

const API_BASE_URL = '/api';

export async function fetchTransactionsByInvoiceId(invoiceId: string) {
    const response = await fetch(`${API_BASE_URL}/transactions/invoice?invoiceCode=${invoiceId}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil data invoice');
    }
    return response.json();
}

export async function fetchTransactions(payload?: Record<string, any>) {
    const queryString = new URLSearchParams(payload).toString();

    const response = await fetch(`${API_BASE_URL}/transactions?${queryString}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil data transaksi');
    }
    return response.json();
}

export async function fetchPostTransaction(data: newTransaction) {
    const response = await fetch(`${API_BASE_URL}/transactions`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })

    if (!response.ok) {
        throw new Error('Gagal melakukan transaksi');
    }
    return response.json();
}

export async function fetchUpdateStatusTransaction(invoiceCode: string, status: string) {
    const response = await fetch(`${API_BASE_URL}/transactions?invoiceCode=${invoiceCode}&status=${status}`, {
        method: 'PUT',
    })

    if (!response.ok) {
        throw new Error('Gagal mengupdate status transaksi');
    }
    return response.json();
}

