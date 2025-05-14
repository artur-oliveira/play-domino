import {isAxiosError} from "axios";
import {toast} from "sonner";
import {ErrorMessage} from "../models/error-message.ts";

type ErrorCode = (
    'dominogame.add-player.user-already-joined-game'
    )

export class ErrorUtils {

    public static getErrorCode(error: Error): ErrorCode | null {
        if (isAxiosError(error) && error.response && error.response.data) {
            return (error.response.data as ErrorMessage).code as ErrorCode ?? null;
        }
        return null;
    }

    public static getErrorMessage(error: Error): string | null {
        if (isAxiosError(error) && error.response && error.response.data) {
            return (error.response.data as ErrorMessage).message ?? error.message;
        }
        return error.message;
    }

    public static displayAxiosError(error: Error): void {
        if (isAxiosError(error)) {
            toast.error(ErrorUtils.getErrorMessage(error))
        }
        console.error(error);
    }
}