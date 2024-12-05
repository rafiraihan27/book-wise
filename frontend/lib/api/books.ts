const API_BASE_URL = '/api';

export async function fetchBooks(payload: Record<string, any>) {
    const queryString = new URLSearchParams(payload).toString();

    const response = await fetch(`${API_BASE_URL}/books?${queryString}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Failed to fetch books');
    }
    return response.json();
}

export async function fetchBookById(id: string) {
    const response = await fetch(`${API_BASE_URL}/books/${id}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Failed to fetch book by id');
    }
    return response.json();
}

export async function fetchBooksRecommendation(max: number) {
    const response = await fetch(`${API_BASE_URL}/books/recommended?max=${max}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Failed to fetch book recommendation');
    }
    return response.json();
}