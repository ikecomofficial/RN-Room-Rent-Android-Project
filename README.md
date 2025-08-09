v1.0.0 - Initial project setup with Firebase Authentication, Property & Room/Shop creation logic implemented. Linked properties and rooms in database using userId and propertyId. Locale-safe formatting and timestamp storage added.

This is an Android Room & Shop Rental Management App built with Java, Firebase Realtime Database, and Firebase Authentication.
The app allows a user to create and manage multiple properties, each containing multiple rooms and shops.
Each room/shop is associated with the property ID and the logged-in user ID for secure and filtered access.

Progress Implemented So Far (v1.0.0)
Authentication

Email/Password Sign-In

Google Sign-In Integration

Property Management

Create new properties with details such as property name and address.

Save properties in Firebase Realtime Database with userId mapping for filtering.

Room & Shop Creation

Auto-generate rooms and shops when a property is created.

Store details like room/shop name, type (isRoom boolean), property ID, user ID, creation timestamp, and sort order.

Database Structure

Properties Node: Stores all user properties.

Rooms Node: Stores all rooms/shops linked to each property.

Data linked using propertyId and userId.

Code Optimizations

Locale-safe naming for rooms/shops (e.g., Room 01, Shop 03).

Server-side timestamps for creation date.

Reusable logic for adding new rooms/shops in sequence.
