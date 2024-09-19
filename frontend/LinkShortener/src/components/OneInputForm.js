import { useState } from "react";
import { useForm } from "react-hook-form";
import { isValidUrl } from "../functions/functions";
import Loader from "./Loader";

export default function OneInputForm() {
    const { register, handleSubmit } = useForm();
	const [data, setData] = useState("");
	const [error, setError] = useState("");
	const [loading, setLoading] = useState(false);

    const reset = () => {
        setData("");
        setError("");
        setLoading(false);
    }

    return (
        <form onSubmit={handleSubmit(async (form) => {
            reset();
            const url = form.url;
            if (!isValidUrl(url)) {
                setError("Вы передали не ссылку!");
                return;
            }

            setLoading(true);

            try {
                const response = await fetch("https://shrtner-api.onrender.com", {
                    method: "POST",
                    headers: {
                        "Content-Type": "plain/text;charset=utf-8"
                    },
                    body: form.url
                });
                
                if (!response.ok) {
                    setError("Возникла ошибка при обработке запроса!");
                    console.error(response);
                    return;
                }
                const responseData = await response.text();
                setData(responseData);
            } catch (error) {
                setError("Произошла ошибка: " + error.message);
            } finally {
                setLoading(false);
            }
		})}>
            <input {...register("url")} />
            <input type="submit" />
			{loading && <Loader/>}
			{data !== "" ? <p>Ваша ссылка: <a href={data}>{data}</a></p> : null}
			{error !== "" ? <p style={{color: "red"}}>{error}</p> : null}
        </form>
    );
}
