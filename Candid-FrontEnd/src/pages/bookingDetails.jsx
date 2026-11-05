import { useRef, useState } from "react"
import CHeader from "../compnents/cheader"

function BookingDetails() {

    const addressLine1 = useRef()
    const addressLine2 = useRef()
    const townCity = useRef()
    const postCode = useRef()
    const country = useRef()
    const [errorMessage, useErrorMessage] = useState()
    const [coord, setCoord] = useState()
    const [active, setActive] = useState("other")
    const message = useRef()


    function submitAddress() {

        let addressString = addressLine1.current.value + ", " + addressLine2.current.value + ", " + townCity.current.value + ", " + postCode.current.value + ", " + country.current.value

        fetch("/api/geo/getcoord", {

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body: JSON.stringify({

                address: addressString

            })

        }).then(response => response.json()).then(async data => {

            console.log(data)

            if (!data.success) {

                if (data.internalCode === 401) {


                    let result = await tokenService.requestJwt()


                    if (result === true) {

                        fetch("/api/geo/getcoord", {

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body: JSON.stringify({

                                address: addressString

                            })

                        }).then(res => res.json()).then(d => {

                            if (d.success) {

                                setCoord(d.data)
                                setActivePage("preference")

                            }
                            else {

                                setErrorMessage(d.error)

                            }

                        })

                    }

                }
                else {

                    setErrorMessage(data.error)

                }

            }
            else {

                setCoord(data.data)
                setActivePage("preference")

            }

        })

    }

    return (
        <div>
            <CHeader />
            <div className="

                    w-full
                    h-full
                    flex
                    justify-center
                    mt-30

                    xl:mt-10
                ">
                <div className={`

                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[60vh]
                        h-[70vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center

                        ${active === "address" ? "" : "hidden"}


                    `}>
                    <p className="

                            mt-20
                            text-2xl    
                            pt-10
                            xl:mt-3

                        ">Enter base location of profile</p>
                    <div className="

                            flex
                            flex-col
                            items-center
                            gap-7
                            mt-10
                            xl:mt-5 
                            xl:gap-5

                        ">
                        <input ref={addressLine1} placeholder="Adress line 1" type="text" className="

                                border-2

                                h-12
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500

                                hover:border-black

                                transition
                                duration-100

                                xl:w-[40vh]


                            "/>
                        <input ref={addressLine2} placeholder="Adress line 2" type="text" className="

                                border-2

                                h-12
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500

                                hover:border-black

                                transition
                                duration-100

                                xl:w-[40vh]

                            "/>
                        <input ref={townCity} placeholder="Town/City" type="text" className="

                                border-2

                                h-12
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500

                                hover:border-black

                                transition
                                duration-100

                                xl:w-[40vh]

                            "/>
                        <input ref={postCode} placeholder="PostCode" type="text" className="

                                border-2

                                h-12
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500

                                hover:border-black

                                transition
                                duration-100

                                xl:w-[40vh]

                            "/>
                        <input ref={country} placeholder="Country" type="text" className="

                                border-2

                                h-12
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500

                                hover:border-black

                                transition
                                duration-100

                                xl:w-[40vh]

                            "/>
                    </div>
                    <button className="

                                text-neutral-500
                                hover:text-black
                                active:text-neutral-500
                                pt-5

                        ">Use User's base location</button>
                    <p className="

                                text-red-600

                        ">{errorMessage}</p>
                    <div className="

                                mt-10
                                flex
                                gap-5
                                xl:mt-5

                        ">
                        <button onClick={submitAddress} className="

                                bg-neutral-700
                                text-white
                                rounded-xl
                                w-[15vh]
                                h-8.75
                                flex
                                items-center
                                justify-center
                                hover:bg-gray-900

                                transition
                                duration-100

                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                active:bg-white
                                active:text-black


                            ">Submit</button>
                    </div>
                </div>

                <div className={`

                    flex
                    flex-col
                    items-center
                    mt-10
                    ${active === "other" ? "" : "hidden"}

                `}>
                    <div className="

                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[90vh]
                        h-[30vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center

                    ">
                        <p className="

                            w-full
                            pl-5
                            pt-5
                            text-[2vh]
                            text-neutral-600
                            font-semibold

                        ">Greeting Message</p>
                        <textarea ref={message} placeholder="Write your greeting message" className="


                            w-9/10
                            h-full
                            mb-10
                            mt-4
                            p-3
                            resize-none
                            border
                            rounded-xl
                            border-neutral-200
                            focus:outline-0

                        ">

                        </textarea>

                    </div>
                    <button onClick={submitAddress} className="

                        bg-neutral-700
                        text-white
                        rounded-xl
                        w-[15vh]
                        h-8.75
                        flex
                        items-center
                        justify-center
                        hover:bg-gray-900

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        mt-10

                    ">Submit</button>
                </div>

            </div>
        </div>
    )

}

export default BookingDetails