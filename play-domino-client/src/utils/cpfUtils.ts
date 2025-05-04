export function formatCPF(value: string) {
    return value
        .replace(/\D/g, '')                     // Remove tudo que não for dígito
        .replace(/(\d{3})(\d)/, '$1.$2')        // Coloca o primeiro ponto
        .replace(/(\d{3})(\d)/, '$1.$2')        // Coloca o segundo ponto
        .replace(/(\d{3})(\d{1,2})$/, '$1-$2'); // Coloca o traço
}

export function isCpfValid(cpf: string): boolean {
    cpf = cpf.replace(/\D+/g, '');

    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;

    let sum = 0;
    for (let i = 0; i < 9; i++) sum += +cpf[i] * (10 - i);
    let firstCheck = (sum * 10) % 11;
    if (firstCheck === 10 || firstCheck === 11) firstCheck = 0;
    if (firstCheck !== +cpf[9]) return false;

    sum = 0;
    for (let i = 0; i < 10; i++) sum += +cpf[i] * (11 - i);
    let secondCheck = (sum * 10) % 11;
    if (secondCheck === 10 || secondCheck === 11) secondCheck = 0;
    return secondCheck === +cpf[10];
}