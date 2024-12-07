import { User } from "@/types/interfaces";

const API_BASE_URL = '/api';

/**
 * Mengambil detail user berdasarkan ID-nya.
 *
 * @param {string} id - ID unik dari user yang ingin diambil.
 * @returns {Promise<any>} Detail dari user yang diminta.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 *
 * Request example: http://localhost:8080/api/users/1
 *
 * Response example:
 * {
 *   "email": "admin@admin.com",
 *   "name": "admin",
 *   "phone": "08123456789",
 *   "role": "admin"
 * }
 */
export async function fetchUserById(id: string) {
    const response = await fetch(`${API_BASE_URL}/users/${id}`, {
        method: 'GET',
    });

    if (!response.ok) {
        throw new Error('Gagal mengambil data user berdasarkan ID');
    }
    return response.json();
}

/**
 * Memperbarui data pengguna berdasarkan ID.
 *
 * @param {string} id - ID unik dari pengguna yang ingin diperbarui.
 * @param {User} data - Objek yang berisi data pengguna baru untuk menggantikan data lama.
 * @returns {Promise<any>} Data pengguna yang diperbarui dari respons API.
 * @throws {Error} Jika permintaan gagal atau respons tidak berhasil (status bukan 2xx).
 *
 * Contoh permintaan (request):
 * PUT http://localhost:8080/api/users/1
 * Body:
 * {
 *   "name": "admin",
 *   "email": "admin@admin.com",
 *   "phone": "08123456789",
 *   "role": "admin"
 *   "nim": "",
 *   "nip": "",
 *   "year": "",
 * }
 *
 * Contoh respons (response):
 * {
 *   "id": "1",
 *   "name": "admin",
 *   "email": "admin@admin.com",
 *   "phone": "08123456789",
 *   "role": "admin"
 * }
 */
export async function updateUserById(id: string, data: User) {
    const response = await fetch(`${API_BASE_URL}/users/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Gagal memperbarui data pengguna');
    }
    return response.json();
}

/**
 * Mendaftarkan mahasiswa baru.
 *
 * @param {User} data - Objek yang berisi data mahasiswa baru yang akan didaftarkan.
 * @returns {Promise<any>} Data mahasiswa yang baru terdaftar.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 *
 * Contoh permintaan (request):
 * POST http://localhost:8080/api/users/register/student
 * Body:
 * {
 *   "name": "John Doe",
 *   "nim": "12345",
 *   "year": "2023",
 *   "phone": "08123456789",
 *   "email": "johndoe@example.com",
 *   "password": "password123"
 * }
 *
 * Contoh respons (response):
 * {
 *   "id": "1",
 *   "name": "John Doe",
 *   "nim": "12345",
 *   "year": "2023",
 *   "email": "johndoe@example.com",
 *   "phone": "08123456789",
 *   "role": "student"
 * }
 */
export async function registerStudent(data: User) {
    const response = await fetch(`${API_BASE_URL}/users/register/student`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Gagal mendaftarkan mahasiswa');
    }
    return response.json();
}

/**
 * Mendaftarkan dosen baru.
 *
 * @param {User} data - Objek yang berisi data dosen baru yang akan didaftarkan.
 * @returns {Promise<any>} Data dosen yang baru terdaftar.
 * @throws {Error} Jika permintaan gagal atau respons tidak OK.
 *
 * Contoh permintaan (request):
 * POST http://localhost:8080/api/users/register/lecturer
 * Body:
 * {
 *   "name": "Dr. Jane Smith",
 *   "nip": "98765",
 *   "phone": "08123456789",
 *   "email": "janesmith@university.edu",
 *   "password": "password123"
 * }
 *
 * Contoh respons (response):
 * {
 *   "id": "2",
 *   "name": "Dr. Jane Smith",
 *   "nip": "98765",
 *   "email": "janesmith@university.edu",
 *   "phone": "08123456789",
 *   "role": "lecturer"
 * }
 */
export async function registerLecturer(data: User) {
    const response = await fetch(`${API_BASE_URL}/users/register/lecturer`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error('Gagal mendaftarkan dosen');
    }
    return response.json();
}