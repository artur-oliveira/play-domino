import {api} from "../lib/axios.ts";
import {ListResponse} from "../models/generic.models.ts";

export const getAllFaq = async (): Promise<ListResponse<{
    answer: string;
    question: string;
}>> => {
    return api.get('/v1/faq').then((res) => res.data);
}