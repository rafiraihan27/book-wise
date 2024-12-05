const API_BASE_URL = '/api';

/**
 * Mengambil daftar buku berdasarkan parameter query yang diberikan.
 *
 * @param {Record<string, any>} payload - Parameter query untuk permintaan {search, category, years}.
 * @returns {Promise<any>} Daftar buku yang sesuai dengan parameter query.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 * 
 * Request example: http://localhost:8080/api/books?search="Dunia Sophie"&category="fiksi"&years=1991
 * 
 * Response example:
 [{
    "id": "1",
    "title": "Dunia Sophie",
    "author": "Jostein Gaarder",
    "category": "Fiksi",
    "year": 1991,
    "description": "Buku yang mengulas sejarah filsafat dalam bentuk cerita.",
    "image": "https://example.com/dunia-sophie.jpg",
    "quota": 5
  }]
 */
export async function fetchBooks(payload: Record<string, any>) {
    const queryString = new URLSearchParams(payload).toString();

    const response = await fetch(`${API_BASE_URL}/books?${queryString}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil data buku');
    }
    return response.json();
}

/**
 * Mengambil detail sebuah buku berdasarkan ID-nya.
 *
 * @param {string} id - ID unik dari buku yang ingin diambil.
 * @returns {Promise<any>} Detail dari buku yang diminta.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 * 
 * Request example: http://localhost:8080/api/books/1
 * 
 * Response example
 {
  "id": "1",
  "title": "Dunia Sophie",
  "author": "Jostein Gaarder",
  "category": "Fiksi",
  "year": 1991,
  "description": "Buku yang mengulas sejarah filsafat dalam bentuk cerita.",
  "image": "https://example.com/dunia-sophie.jpg",
  "quota": 5,
  "rackNumber": "A1",
  "mainSubject": "Filsafat",
  "isbn": "978-0-14-044913-6",
  "language": "Indonesia",
  "availableCopies": 2,
  "lateFee": 1000,
  "canBorrow": true,
  "rating": 4.5,
  "reviews": [{
      "id": "r1",
      "author": "John Doe",
      "date": "2023-01-01",
      "rating": 5,
      "content": "Buku yang sangat menginspirasi!"
    }]
 }
 */
export async function fetchBookById(id: string) {
    const response = await fetch(`${API_BASE_URL}/books/${id}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil data buku berdasarkan ID');
    }
    return response.json();
}

/**
 * Mengambil rekomendasi buku dengan jumlah maksimum yang ditentukan.
 *
 * @param {number} max - Jumlah maksimum buku rekomendasi yang diambil.
 * @returns {Promise<any>} Daftar buku rekomendasi.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 * 
 * Request example: http://localhost:8080/api/books/recommended?max=4
 * 
 * Response example
 [{
    "id": "1",
    "title": "Dunia Sophie",
    "author": "Jostein Gaarder",
    "category": "Fiksi",
    "year": 1991,
    "description": "Buku yang mengulas sejarah filsafat dalam bentuk cerita.",
    "image": "https://example.com/dunia-sophie.jpg",
    "quota": 5
  }]
 */
export async function fetchBooksRecommendation(max: number) {
    const response = await fetch(`${API_BASE_URL}/books/recommended?max=${max}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil rekomendasi buku');
    }
    return response.json();
}
