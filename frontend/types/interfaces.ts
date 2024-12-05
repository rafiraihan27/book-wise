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
