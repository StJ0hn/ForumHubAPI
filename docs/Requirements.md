# Requirements

## Problem
Discussions about course-related topics happen in a scattered, uncentralized way,
with no dedicated space for users to ask questions and exchange ideas asynchronously.

## Objective
Provide an environment for creating discussion topics on general subjects, enabling questions to be answered and ideas exchanged through messages.

## Scope

## Scope

### In Scope
- User authentication (login)
- Create a discussion topic (title + message)
- View topics created by other users
- View topics created by user
- Reply to another user's topic (new feature — not yet implemented)
- Partial update of a topic's title and/or message (owner only)
- Delete a topic (owner only — soft delete, not yet wired to an endpoint)

### Out of Scope
- Moderation roles (admin/moderator) — single user role only
- Likes/upvotes on topics or replies
- Marking a reply as accepted solution
- Notifications
- Tags or free-text search

## Actors
- **User** — an authenticated person who can create topics, view topics created by
  other users, reply to topics, and manage (edit/delete) only the topics they
  authored. There is a single user role; no moderator/admin distinction exists.


## Functional Requirements

### RF01 — User authentication
The system must authenticate users via login credentials (email and password)
and issue a JWT token upon success.

### RF02 — Create topic
The system must allow an authenticated user to create a discussion topic with
a title and a message.

### RF03 — View topics from other users
The system must allow an authenticated user to view topics created by other users.

### RF04 — View own topics
The system must allow an authenticated user to view, in a separate view, only
the topics they have authored.

### RF05 — Reply to topic
The system must allow an authenticated user to reply to a topic created by
another user.

### RF06 — Update topic
The system must allow the topic's author to update the title and/or message
independently, submitting one field must not erase the other.

### RF07 — Delete topic
The system must allow the topic's author to delete a topic they created.
Deletion must be logical (soft delete), not physical removal from the database.

### RF08 — Paginated topic listing
The system must return the topic listing in paginated form.

## Non-Functional Requirements

### RNF01 — Statelessness
The system must not maintain server-side session state; all authentication
must be handled via JWT tokens on each request.

### RNF02 — Data integrity on schema changes
All database schema changes must be applied through versioned migrations
(Flyway), never through automatic schema generation.

### RNF03 — Credential and secret management
No credentials or cryptographic secrets (database password, JWT signing key)
may be stored in plain text within version-controlled files. All secrets
must be supplied via environment variables.

## Business Rules

### RN01 — Resource ownership
A user may only edit or delete topics they authored. Attempting to modify
another user's topic must be rejected, regardless of authentication status.

### RN02 — Deleted topics are hidden
A topic marked as deleted (soft delete) must not appear in any topic listing
or detail view, regardless of who is requesting it.