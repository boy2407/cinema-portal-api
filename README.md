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

## Payment Service (Separated Architecture)

A dedicated **Payment Service** microservice handles all payment processing, separate from the Cinema Portal API for better scalability and maintainability.

### Architecture Overview

```
Client (Frontend)
  ↓
Cinema Portal API (Booking & Ticket)
  ↓
Payment Service (VNPAY Integration)
  ↓
VNPAY Payment Gateway
```

### Processing Flow

1. **Client initiates payment:**
   - Frontend sends seat booking request to Cinema Portal API with user ID and seat IDs.

2. **Cinema Portal API creates pending order:**
   - Creates a `Ticket` with status `PENDING`.
   - Generates `bookingNumber` for order tracking.
   - Reserves selected `ShowSeat` records temporarily.

3. **Cinema Portal API calls Payment Service:**
   - Sends payment request to Payment Service with:
     - Order ID (bookingNumber)
     - Amount (total ticket price)
     - User information
     - Order metadata

4. **Payment Service initiates VNPAY payment:**
   - Generates payment URL with transaction details.
   - Redirects user to VNPAY checkout page.

5. **VNPAY processes payment & returns callback:**
   - User completes payment on VNPAY gateway.
   - VNPAY sends IPN callback to Payment Service.

6. **Payment Service verifies and confirms payment:**
   - Verifies HMAC SHA-256 signature from VNPAY.
   - Validates order amount and transaction details.
   - Updates payment status (`SUCCESS` / `FAILED`).

7. **Payment Service notifies Cinema Portal API:**
   - Sends payment confirmation webhook to Cinema Portal API.
   - Cinema Portal API updates `Ticket` status to `PAID` and finalizes seat reservation.
   - QR code ticket is generated and stored.

### Reliability and Security Controls

- **Signature Verification:** Validate HMAC SHA-256 signature from VNPAY callback.
- **Order Validation:** Verify order ID and amount against server-side records.
- **Idempotency:** Use idempotency keys to safely handle duplicate webhook calls.
- **Timeout Handling:** Implement timeout logic to reconcile unconfirmed payments.
- **Status Consistency:** Atomic updates for payment status to prevent race conditions.
- **Payment Reference:** Store VNPAY transaction reference for auditing and refunds.

### Webhook Callback Endpoint (Cinema Portal API)

The Payment Service will send a webhook to Cinema Portal API:

```
POST /api/payments/webhook/confirmation
{
  "bookingNumber": "BK123456",
  "status": "SUCCESS|FAILED",
  "transactionId": "vnpay_transaction_id",
  "amount": 150000,
  "timestamp": "2026-04-15T10:30:00Z",
  "signature": "hmac_signature"
}
```

### Core Implementation References

- Cinema Portal API (Ticket & Booking):
  - `src/main/java/com/uit/cinemaportalapi/service/impl/TicketServiceImpl.java`
  - `src/main/java/com/uit/cinemaportalapi/entity/Ticket.java`
  - `src/main/java/com/uit/cinemaportalapi/controller/TicketController.java`

- Payment Service (separate repository):
  - `payment-service/src/main/java/com/uit/paymentservice/controller/PaymentController.java`
  - `payment-service/src/main/java/com/uit/paymentservice/service/impl/VNPayServiceImpl.java`
  - `payment-service/src/main/java/com/uit/paymentservice/entity/Payment.java`

## Project Status Summary

- QR ticket flow: **Implemented** and used in booking.
- Payment Service: **Planned** - separate microservice with VNPAY integration.
- Cinema Portal API: **Refactoring** - integrate with Payment Service via webhooks.
- VNPAY Integration: **In Progress** - Payment Service development.


