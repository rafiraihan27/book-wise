export interface Review {
    id: string;
    author: string;
    date: string;
    rating: number;
    content: string;
}

export interface Book {
    id: string;
    title: string;
    author: string;
    category: string;
    year: number;
    description: string;
    image: string;
    quota: number;
    rackNumber: string;
    mainSubject: string;
    isbn: string;
    language: string;
    availableCopies: number;
    lateFee: number;
    canBorrow: boolean;
    rating: number;
    reviews: Review[];
}

export interface SimpleBook {
    id: string;
    title: string;
    author: string;
    category: string;
    year: number;
    description?: string;
    image: string;
    quota: number;
}

export interface CreateUpdateBook {
    id?: string;
    title: string;
    author: string;
    category: string;
    year: number;
    description: string;
    image: string;
    quota: number;
    rackNumber: string;
    mainSubject: string;
    isbn: string;
    language: string;
    availableCopies: number;
    lateFee: number;
    canBorrow: boolean;
    rating?: number;
}

export interface Notification {
    id: string
    userId: string
    title: string
    message: string
    type: "info" | "reminder" | "alert"
    date: string
    read: boolean
  }

export interface User {
    id?: string,
    email: string,
    password?: string,
    name: string,
    phone?: string,
    role: "admin" | "student" | "lecturer",
    nim?: string,
    nip?: string,
    year?: string,
}