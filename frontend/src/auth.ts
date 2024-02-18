export function getNameFromToken(jwt: string): any {
    const base64Url = jwt.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const decodedBytes = new Uint8Array(Array.from(atob(base64), (char) => char.charCodeAt(0)));
    const decoder = new TextDecoder('utf-8');
    const decodedData = decoder.decode(decodedBytes);

    return JSON.parse(decodedData).sub;
}