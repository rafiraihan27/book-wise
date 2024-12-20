CREATE TABLE users (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    role ENUM('admin', 'student', 'lecturer') NOT NULL,  -- Menggunakan ENUM untuk role
    phone VARCHAR(255) UNIQUE,
    nim VARCHAR(255) UNIQUE,
    nip VARCHAR(255) UNIQUE,
    year VARCHAR(255)
);

CREATE TABLE books (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    title VARCHAR(255) NOT NULL UNIQUE,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    year INTEGER,
    description TEXT,
    image VARCHAR(255),
    quota INTEGER,
    rack_number VARCHAR(255),
    isbn VARCHAR(255) NOT NULL UNIQUE,
    language VARCHAR(255),
    available_copies INTEGER,
    late_fee DECIMAL(10,2),
    can_borrow BOOLEAN DEFAULT true,
    rating DECIMAL(3,2)
);

CREATE TABLE transactions (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    user_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    invoice_code VARCHAR(255) NOT NULL UNIQUE,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    total_fee DOUBLE NOT NULL,  -- DOUBLE digunakan untuk nilai numerik
    status ENUM('PENDING', 'APPROVED', 'DECLINED', 'OVERDUE') NOT NULL,  -- ENUM untuk status
    type ENUM('BORROW', 'RETURN') NOT NULL,  -- ENUM untuk tipe transaksi
    payment_method VARCHAR(255),
    payment_evidence VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transaction_items (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    transaction_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    book_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    late_fee DECIMAL(10,2),
    title VARCHAR(255) NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

CREATE TABLE reviews (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    book_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    author_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    date TIMESTAMP NOT NULL,  -- Menggunakan TIMESTAMP untuk tanggal
    rating DOUBLE NOT NULL,
    content TEXT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id CHAR(36) PRIMARY KEY NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    user_id CHAR(36) NOT NULL,  -- Menggunakan CHAR(36) untuk UUID
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    type ENUM('INFO', 'REMINDER', 'ALERT') NOT NULL,  -- ENUM untuk tipe notifikasi
    date TIMESTAMP NOT NULL,
    `read` BOOLEAN NOT NULL DEFAULT false,  -- Escaping 'read' with backticks
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

