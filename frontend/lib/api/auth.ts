const API_BASE_URL = '/api';

/**
 * Fungsi untuk login user (student atau lecturer).
 *
 * @param {string} email - Email user.
 * @param {string} password - Password user.
 * @returns {Promise<any>} Data user jika login berhasil.
 * @throws {Error} Jika login gagal atau respons tidak OK.
 *
 * Request example: POST http://localhost:8080/api/auth/login
 * Body:
 * {
 *   "email": "example@student.com",
 *   "password": "password123"
 * }
 *
 * Response example:
 * {
 *   "id": "1",
 *   "email": "example@student.com",
 *   "name": "John Doe",
 *   "role": "student",
 *   "token": "jwt-token-here"
 * }
 */
export async function loginUser(email: string, password: string) {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
        throw new Error('Gagal login user');
    }
    return response.json();
}

export async function loginUserAdmin(email: string, password: string) {
    const response = await fetch(`${API_BASE_URL}/auth/login/admin`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
        throw new Error('Gagal login user');
    }
    return response.json();
}

/**
 * Fungsi untuk registrasi user (student atau lecturer).
 *
 * @param {Record<string, any>} payload - Data registrasi user.
 * @returns {Promise<any>} Data user yang berhasil diregistrasi.
 * @throws {Error} Jika registrasi gagal atau respons tidak OK.
 *
 * Request example: POST http://localhost:8080/api/auth/register
 * Body:
 * {
 *   "email": "example@student.com",
 *   "password": "password123",
 *   "role": "student",
 *   "name": "John Doe",
 *   "NIM": "123456789",
 *   "NIP": "",
 *   "year": 2023,
 *   "phone": "081234567890"
 * }
 *
 * Response example:
 * {
 *   "message": "success"
 * }
 */
export async function registerUser(payload: Record<string, any>) {
    const response = await fetch(`${API_BASE_URL}/auth/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    });

    if (!response.ok) {
        throw new Error('Gagal registrasi user');
    }
    return response.json();
}
