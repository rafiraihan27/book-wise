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