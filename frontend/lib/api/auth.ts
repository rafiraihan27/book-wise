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
    const response = await fetch(`${process.env.API_BASE_URL}/auth/login`, {
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
    const response = await fetch(`${process.env.API_BASE_URL}/auth/login/admin`, {
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
