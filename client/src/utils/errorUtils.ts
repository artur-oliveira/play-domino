import {isAxiosError} from "axios";
import {toast} from "sonner";
import {ErrorMessage} from "../models/error-message.ts";

export class ErrorUtils {
    public static displayAxiosError(error: Error): void {
        if (isAxiosError(error)) {
            if (error.response && error.response.data) {
                toast.error((error.response?.data as ErrorMessage).message.toString());
            } else {
                toast.error(error.message.toString());
            }
        }
        console.error(error);
    }
}