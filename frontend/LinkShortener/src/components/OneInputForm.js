import { useState } from "react";
import { useForm } from "react-hook-form";
import { isValidUrl } from "../functions/functions";

export default function OneInputForm({info="info"}) {
    const { register, handleSubmit } = useForm();
	const [data, setData] = useState("");
	const [error, setError] = useState("");

	const reset = () => {
		setData("");
		setError("");
	}

    return (
        <form onSubmit={handleSubmit(async (form) => {
			reset();
			const url = form.url;
			if (!isValidUrl(url)) {
				setError("Вы передали не ссылку!");
				return;
			}
			const response = await fetch("https://shrtner-api.onrender.com/api", {
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
		})}>
            <input {...register("url")} />
            <input type="submit" />
			{data !== "" ? <p>Ваша ссылка: <a href={data}>{data}</a></p> : null}
			{error !== "" ? <p style={{color: "red"}}>{error}</p> : null}
        </form>
    );
}
