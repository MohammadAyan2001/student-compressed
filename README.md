Student Data API (CSV Upload & Compression)
Project Overview

This is a simple Student Data API that allows uploading student information in CSV format. The backend compresses and decompresses the data to optimize storage and improve performance.

This is a small proof-of-concept (POC) but demonstrates a pattern that can be useful in large-scale applications where data is frequently uploaded from files.

Features

Upload student data in CSV format.

Compress data on the frontend to reduce HTTP payload.

Decompress and save data in the database on the backend.

Reduces network load and improves performance.

Easily extendable to larger projects handling file-based data.

How It Works

User uploads a CSV file containing student data.

The frontend compresses the CSV data to reduce the size.

The backend receives the compressed data, decompresses it, and stores it in the database.

The system efficiently handles data, decreasing HTTP payloads and improving API performance.
