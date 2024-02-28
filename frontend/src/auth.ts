import axios from "axios";

function decodeToken(jwt: string):any {
    const base64Url = jwt.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const decodedBytes = new Uint8Array(Array.from(atob(base64), (char) => char.charCodeAt(0)));
    const decoder = new TextDecoder('utf-8');
    const decodedData = decoder.decode(decodedBytes);
    return JSON.parse(decodedData);
}

export function getNameFromToken(jwt: string): any {
    return decodeToken(jwt).nickname;
}

export function getUserIdFromToken(jwt: string): any {
    return decodeToken(jwt).userId
}

export function verifyUser(jwt: string): Promise<string> {
    return new Promise((resolve) => {
        axios.post(process.env.VUE_APP_BACKEND_ADDRESS+"/auth/verifyUser", {}, {
            headers: { Authorization: jwt }
        })
        .then((res) => {
            if (res.data === true) resolve("Valid User")
            else resolve("Expired User")
        })
        .catch(() => { resolve("Invalid User") })
    })
}