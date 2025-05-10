import {useQuery} from "@tanstack/react-query";
import {getAllFaq} from "../services/faq.service.ts";


export const useGetAllFaq = () => {
    return useQuery({
        queryKey: ["faq"],
        queryFn: getAllFaq
    });
};