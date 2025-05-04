export interface WalletResponse {
    feePercent: number;
    minimumDepositCents: number;
    minimumWithdrawCents: number;
    minimumBetCents: number;
    maximumDepositCents: number;
    maximumBetCents: number;
    maximumWithdrawCents: number;
    availableCents: number;
    displayAvailableCents: string;
    lockedCents: number;
    displayLockedCents: string;
    pendingWithdrawCents: number;
    displayPendingWithdrawCents: string;
    pendingDepositCents: number;
    displayPendingDepositCents: string;
}

export type TransactionType =
    'DEPOSIT'
    | 'WITHDRAW'
    | 'CANCEL_WITHDRAW'
    | 'CONFIRM_WITHDRAW'
    | 'GAME_ENTRY'
    | 'GAME_PRIZE'
    | 'FEE'
    | 'ADJUSTMENT';

export interface WalletTransactionResponse {
    id: number;
    type: TransactionType;
    incoming: boolean;
    displayAmountCents: string;
    amountCents: number;
    description: string;
    createdAt: string;
    createdById: number;
    createdByName: string;
}

export interface WalletAmountPayload {
    amountCents: number;
}