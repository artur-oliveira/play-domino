const dateFormatters: Record<string, Intl.DateTimeFormat> = {
    'pt-BR': new Intl.DateTimeFormat('pt-BR', {})
}
const dateTimeFormatters: Record<string, Intl.DateTimeFormat> = {
    'pt-BR': new Intl.DateTimeFormat('pt-BR', {
        dateStyle: 'short',
        timeStyle: 'short',
    })
}

export function formatDate(
    value: string,
    locale: string = 'pt-BR',
): string {
    return dateFormatters[locale].format(new Date(value));
}

export function formatDateTime(
    value: string,
    locale: string = 'pt-BR',
): string {
    return dateTimeFormatters[locale].format(new Date(value));
}