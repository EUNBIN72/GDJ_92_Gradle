export default function Login() {

    function login(e) {
        e.preventDefault();

        const form = new FormData(e.target);

        let all = Object.fromEntries(form.entries())

        console.log(all)
    }

    return(
        <>
            <h1>Login Page</h1>
            <form onSubmit={login}>
                <div>
                    <input type="text" name="username"></input>
                    <input type="password" name="password"></input>
                    <button>Login</button>
                </div>
            </form>
            
        </>
    )
}