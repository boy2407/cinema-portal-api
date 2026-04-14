# Cinema Portal API

Backend API for a cinema booking system built with Spring Boot.

## Implemented Features

- Movie, cinema, screen, showtime, seat, and ticket management APIs.
- Seat booking flow with ticket creation and seat reservation update.
- Booking number generation for each ticket for transaction tracking.
- QR code ticket generation using ZXing and Base64 storage in `Ticket.qrCode`.
- Ticket information and payment history retrieval for user check-in and history screens.

## QR Code Ticket Processing (Implemented)

1. Client sends seat IDs, user ID, and subtotal to booking API.
2. Backend creates a `Ticket` and generates a `bookingNumber`.
3. Selected `ShowSeat` records are linked to the ticket and marked reserved.
4. A QR code image is generated from ticket content and saved as Base64 in `Ticket.qrCode`.
5. Ticket details and QR data are returned by ticket info and payment history APIs.

### Core implementation references

- `src/main/java/com/uit/cinemaportalapi/service/impl/ShowSeatServiceImpl.java` (`bookingSeats`)
- `src/main/java/com/uit/cinemaportalapi/service/impl/TicketServiceImpl.java` (`createTicket`, `getTicketInfo`, `getTicketsByUserId`)
- `src/main/java/com/uit/cinemaportalapi/entity/Ticket.java` (`qrCode`, `bookingNumber`)

## MoMo Payment Processing (In Progress)

Current status: payment processing flow is designed and being prepared for integration.

Planned processing flow:

1. Create a pending order for selected seats and amount.
2. Call MoMo create-payment API and redirect user to checkout.
3. Receive callback/IPN from MoMo after payment.
4. Verify HMAC SHA-256 signature, order ID, and amount.
5. Apply idempotent status update (`PAID` / `FAILED`) to handle retries safely.
6. Finalize ticket issuance and persist transaction reference after confirmed payment.

### Reliability and security controls

- Verify callback signature before processing.
- Validate order metadata against server-side records.
- Use idempotency strategy for duplicate IPN/webhook calls.
- Confirm payment success before seat finalization.

## Project Status Summary

- QR ticket flow: implemented and used in booking.
- MoMo integration: processing design completed, implementation in progress.


