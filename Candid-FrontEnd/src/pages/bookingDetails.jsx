import { useRef, useState } from "react"
import CHeader from "../compnents/cheader"
import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router-dom"
import { jwtService } from "../logic/jwt"
import Radial from "../compnents/radial"

function BookingDetails() {

    const addressLine1 = useRef()
    const addressLine2 = useRef()
    const townCity = useRef()
    const postCode = useRef()
    const country = useRef()
    const [errorMessage, setErrorMessage] = useState()
    const [coord, setCoord] = useState()
    const [active, setActive] = useState("other")
    const message = useRef()
    let addressString = ""
    const location = useLocation()
    const bookingId = location.state 
    const [activeCat, setActiveCat] = useState("")
    
    const jwt = new jwtService()
    const navigate = new useNavigate();

    function submitAddress() {

        addressString = addressLine1.current.value + ", " + addressLine2.current.value + ", " + townCity.current.value + ", " + postCode.current.value + ", " + country.current.value

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
                                saveDetails()

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
                saveDetails()

            }

        })

    }

    function categoryClick(text){

        if(text === active){

            setActiveCat("None selected")

        }
        else{
            setActiveCat(text)
        }

    }

    function saveDetails(){

        fetch("/api/booking/client/adddetails",{

            method:"POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body: JSON.stringify({

                slotid:bookingId,
                startslot:-1,
                endslot:-1,
                dateofshoot:null,
                clientid:-1,
                photographerid:-1,
                status:null,
                message:message.current.value,
                address:addressString,
                latitude:coord[0],
                longitude:coord[1],
                category:activeCat

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await jwt.requestJwt()
                    if(result){

                        fetch("/api/booking/client/adddetails",{

                            method:"POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body: JSON.stringify({

                                slotid:bookingId,
                                startslot:-1,
                                endslot:-1,
                                dateofshoot:null,
                                clientid:-1,
                                photographerid:-1,
                                status:null,
                                message:message.current.value,
                                address:addressString,
                                latitude:coord[0],
                                longitude:coord[1]

                            })

                        }).then(res => res.json()).then(d => {

                            if(d.success){

                                navigate("/temp")

                            }else{

                                console.log(error)

                            }

                        })

                    }

                }else{

                    console.log("error")

                }

            }else{

                navigate("/temp")

            }

        })

    }

    function next(){

        if(message.current.value !== ""){

            if(activeCat !== ""){

                setActive("address")

            }
            else{

                setErrorMessage("must select a category")

            }

        }else{

            setErrorMessage("must enter a greeting")

        }

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
                                focus:border-black
                                focus:outline-0

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
                                focus:border-black
                                focus:outline-0

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
                                focus:border-black
                                focus:outline-0

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
                                focus:border-black
                                focus:outline-0

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
                                focus:border-black
                                focus:outline-0

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
                    <div className="
                    
                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[90vh]
                        h-[20vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center
                        mt-10
                    
                    ">
                        <p className="
                        
                            w-full
                            font-semibold
                            text-[2vh]
                            text-neutral-600s
                            pl-5
                            pt-5

                        ">Select Category</p>
                        <div className="
                        
                            border-b-2
                            w-[80vh]
                            border-neutral-300
                            h-[8vh]
                            flex
                            gap-1
                            shrink-0
                            flex-row
                            overflow-y-auto
                            items-center

                        
                        ">  
                            <Radial active={activeCat} onClick={()=>categoryClick("Wedding")} text={"Wedding"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Street")} text={"Street"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Portrait")} text={"Portrait"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Event")} text={"Event"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Fine Art")} text={"Fine Art"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Landscape")} text={"Landscape"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Wild Life")} text={"Wild Life"}/>  
                            <Radial active={activeCat} onClick={()=>categoryClick("Fashion")} text={"Fashion"}/>  
                            <Radial active={activeCat} onClick={()=>categoryClick("Comercial")} text={"Comercial"}/>  
                            <Radial active={activeCat} onClick={()=>categoryClick("Architectural")} text={"Architectural"}/>  
                            <Radial active={activeCat} onClick={()=>categoryClick("Food")} text={"Food"}/>  
                            <Radial active={activeCat} onClick={()=>categoryClick("Product")} text={"Product"}/> 
                            <Radial active={activeCat} onClick={()=>categoryClick("Sports")} text={"Sports"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Documentary")} text={"Documentary"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Photojournalism")} text={"Photo Journalism"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Macro")} text={"Macro"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Astrophotography")} text={"Astrophotography"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Aerial")} text={"Aerial"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Underwater")} text={"Underwater"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Night")} text={"Night"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Long-Exposure")} text={"Long-Exposure"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Black & White")} text={"Black & White"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Scientific")} text={"Scientific"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Medical")} text={"Medical"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Real-Estate")} text={"Real-Estate"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Automotive")} text={"Automotive"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Pet")} text={"Pet"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Newborn & Maternity")} text={"Newborn & Maternity"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Industrial")} text={"Industrial"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Still-Life")} text={"Still-Life"}/>
                            <Radial active={activeCat} onClick={()=>categoryClick("Experimental")} text={"Experimental"}/>
                        </div>
                    </div>
                    <p className="
                    
                        text-red-600
                        mt-10
                    
                    ">{errorMessage}</p>
                    <button onClick={next} className="

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