import { Link } from "react-router-dom"

function SignIn() {
    return (
        <div
            className="
                w-full
                h-screen
                overflow-hidden
                bg-cover
                bg-center
                bg-no-repeat
                flex
                items-center
                justify-center
            "
            style={{
                backgroundImage: "url('/src/resources/ssscales (1).svg')"
            }}
        >

            <div className="
            
                flex
                flex-col
                bg-white
                rounded-md
                w-[40vh]
                h-[50vh]
                shadow-[0_0_10px_rgba(0,0,0,0.25)]
                items-center
            
            ">
                <p className="
                
                    text-4xl
                    font-bold
                    tracking-widest
                    pt-8
                
                ">Welcome</p>
                <p className="
                
                    text-2xl
                    tracking-wide
                    pt-8
                
                ">Enter Your Details</p>
                <input placeholder="UserName" type="text" className="
                
                    border-2
                    mt-10
                    h-10
                    rounded-md
                    w-[30vh]
                    pl-2
                    border-neutral-500
                    hover:border-black

                    transition
                    duration-100

                "/>
                <input placeholder="Password" type="password" className="
                
                    border-2
                    mt-10
                    h-10
                    rounded-md
                    w-[30vh]
                    pl-2
                    border-neutral-500
                    
                    hover:border-black

                    transition
                    duration-100

                
                "/>
                <Link className="
                
                    text-gray-500
                    pt-3

                    hover:text-gray-700
                    active:text-black

                    transition
                    duration-100
                
                ">Forgot Password</Link>
                <button className="
            
                    bg-black
                    w-[30vh]
                    h-10
                    rounded-md
                    text-white

                    hover:bg-gray-900
                    mt-10

                    transition
                    duration-100

                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                    active:bg-white
                    active:text-black

                    

                
                ">Submit</button>
            </div>

        </div>
    )
}

export default SignIn