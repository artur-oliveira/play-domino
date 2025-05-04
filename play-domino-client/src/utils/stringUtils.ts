export function onlyStringDigits(str: string) {
    return (str || '').replace(/\D/g, '');

}