const formatters: Record<string, Intl.NumberFormat> = {
    'pt-BR_BRL': new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    })
}

export function formatCurrency(
    value: string | number,
    locale: string = 'pt-BR',
    currencyCode: string = 'BRL',
): string {

    const cleaned = value.toString().replace(/\D/g, "");
    const numberValue = parseFloat(cleaned) / 100;
    if (isNaN(numberValue)) return "";
    return (formatters[`${locale}_${currencyCode}`] || formatters['pt-BR_BRL']).format(numberValue);
}