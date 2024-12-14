/** @type {import('next').NextConfig} */

import dotenv from 'dotenv';
dotenv.config();

const nextConfig = {
    reactStrictMode: false,
    images: {
        domains: ['images-na.ssl-images-amazon.com'], // Add the domain here
    },
    env: {
        API_BASE_URL: process.env.API_BASE_URL || "/api",
        API_BASE_URL_PRODUCTION: process.env.API_BASE_URL_PRODUCTION || "/api",
    },
};

export default nextConfig;
