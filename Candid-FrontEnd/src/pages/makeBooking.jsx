import CHeader from "../compnents/cheader"
import { useEffect } from "react"
import { jwtService } from "../logic/jwt"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

function MakeBooking(){

    const [scheduleSlots, setScheduleSlots] = useState(() => {
        const slots = []

        for (let i = 0; i < 7; i++) {
            const week = []

            for (let j = 0; j < 48; j++) {
                week.push({
                    startslot: j,
                    endslot: j,
                    dayofweek: i,
                    status: ""
                })
            }

            slots.push(week)
        }

        return slots
    })
    const photographerId = 2
    const tokenService = new jwtService()
    const [retrievedSchedule, setRetrievedSchedule] = useState([])
    const [retrievedBookings, setRetrievedBookings] = useState([])
    const navigate = useNavigate()
    const [selected, setSelected] = useState([])
    const [errorText, setErrorText] = useState([])
    const [date, setDate] = useState(new Date())
    const [week, setWeek] = useState("")
    const[ogWeek, setOgWeek] = useState("")


    useEffect(() => {

        tokenService.checkTokenClient()
        const tempDate = new Date(date)
        if(tempDate.getDay() === 0){

            tempDate.setDate(tempDate.getDate() - (tempDate.getDay() - 6))
            
        }
        else{

            tempDate.setDate(tempDate.getDate() - (tempDate.getDay() - 1))

        }

        setWeek("" + tempDate.toLocaleDateString("en-GB", {month: "short"}) +  " " + tempDate.getDate())
        setOgWeek(tempDate)
        setDate(tempDate)


        fetch("/api/booking/getschedule", {

            method:"POST",
            headers:{

                "Content-Type":"application/json",
                auth:localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                userId:photographerId

            })

        }).then(response => response.json()).then(async data => {

            console.log(data.data)

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await tokenService.requestJwt()
                    if(result){

                        fetch("/api/booking/getschedule", {

                            method:"POST",
                            headers:{

                                "Content-Type":"application/json",
                                auth:localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                userId:photographerId

                            })

                        }).then(r => r.json()).then(d => {

                            if(!d.success){

                                setErrorText(d.error)

                            }
                            else{

                                setRetrievedSchedule(d.data)
                                console.log(d.data)

                            }
                            
                        })

                    }
                    else{

                        navigate("/rejectedjwt")

                    }

                }else{

                    setErrorText(data.error)
                    
                }

            }else{

                setRetrievedSchedule(data.data)
                console.log(data.data)

            }

        })

        fetch("/api/booking/getbookingslots",{

            method:"POST",
            headers:{

                "Content-Type":"application/json",
                "auth":localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                userId:"2"

            })

        }).then(response => response.json()).then(async data => {

            if(!data.success){

                if(data.internalCode === 401){

                    let result = await tokenService.requestJwt()
                    if(!result){

                        navigate("rejectedjwt")

                    }
                    else{

                        fetch("/api/booking/getbookingslots",{

                            method:"POST",
                            headers:{

                                "Content-Type":"application/json",
                                "auth":localStorage.getItem("jwt")

                            },
                            body:JSON.stringify({

                                userId:"2"

                            })

                        }).then(r => r.json()).then(d => {

                            if(d.success){

                                setRetrievedBookings(d.data)

                            }else{

                                setErrorText(errorText, d.error)

                            }

                        })

                    }

                }
                else{

                    setErrorText(errorText, data.error)
                    
                }

            }else{

                setRetrievedBookings(data.data)
                
            }

        })

    }, [])

    useEffect(() => {
        
        if (retrievedSchedule.length === 0) {
            return
        }

        setScheduleSlots(prev => {
            const updated = prev.map(day => [...day])

            for (let i = 0; i < retrievedSchedule.length; i++) {

                let slot = retrievedSchedule[i].startslot
                const end = retrievedSchedule[i].endslot
                const day = retrievedSchedule[i].dayofweek

                while (slot <= end) {

                    updated[day][slot] = {...updated[day][slot], status: "schedule"}
                    slot++
                }
            }

            return updated
        })
    }, [retrievedSchedule])

    function revert(){

        setScheduleSlots(() => {
        const slots = []

        for (let i = 0; i < 7; i++) {
            const week = []

            for (let j = 0; j < 48; j++) {
                week.push({
                    startslot: j,
                    endslot: j,
                    dayofweek: i,
                    status: ""
                })
            }

            slots.push(week)
        }

        return slots
        })

        if (retrievedSchedule.length === 0) {
            return
        }

        setScheduleSlots(prev => {
            const updated = prev.map(day => [...day])

            for (let i = 0; i < retrievedSchedule.length; i++) {
                let slot = retrievedSchedule[i].startslot
                const end = retrievedSchedule[i].endslot
                const day = retrievedSchedule[i].dayofweek

                while (slot <= end) {

                    updated[day][slot] = {...updated[day][slot], status: "schedule"}
                    slot++
                }
            }

            return updated
        })

        setErrorText("")

    }

    useEffect(() => {

        console.log(scheduleSlots)

    }, [scheduleSlots])

    useEffect(() => {

        if (retrievedSchedule.length === 0) {
            return
        }

        setScheduleSlots(prev => {
            const updated = prev.map(day => [...day])

            for (let i = 0; i < retrievedBookings.length; i++) {
                
                let slot = retrievedBookings[i].startslot
                const end = retrievedBookings[i].endslot
                const day = retrievedBookings[i].dayofweek

                while (slot <= end) {

                    updated[day][slot] = {...updated[day][slot], status: retrievedBookings.status}
                    slot++
                }
            }

            return updated
        })

    }, [retrievedBookings])

    function nextWeek(){

        let tempDate = new Date(date)

        tempDate.setDate(tempDate.getDate() + 7)
        setWeek(tempDate.toLocaleDateString("en-GB", {month: "short"}) + " " + tempDate.getDate())

        setDate(tempDate)

    }

    function backWeek(){

        let tempDate = new Date(date)

        tempDate.setDate(tempDate.getDate() - 7)

        if(tempDate < ogWeek){
            return
        }

        setWeek(
            tempDate.toLocaleDateString("en-GB", {month: "short"}) +
            " " +
            tempDate.getDate()
        )

        setDate(tempDate)

    }

    function selectSlot(status, startSlot, day) {

        setScheduleSlots(prev => {

            const updated = prev.map(daySlots => [...daySlots])

            for (let i = 0; i < prev.length; i++) {

                for (let j = 0; j < prev[i].length; j++) {

                    if ( prev[i][j].startslot === startSlot && prev[i][j].dayofweek === day){

                        if(status === "schedule"){

                            updated[i][j] = {...updated[i][j],status: "selected"}

                        }
                        else if(status === "selected"){

                           updated[i][j] = {...updated[i][j],status: "schedule"}

                        }

                        return updated
                    }
                }
            }


            return updated
        })
    }

    function submit(){

        let slotList = []
        let selectedAny = false

        for(let i = 0; i < scheduleSlots.length; i++){

            let temp = []

            for(let j = 0; j < scheduleSlots[i].length; j++){


                if(scheduleSlots[i][j].status === "selected"){

                    selectedAny = true

                    temp.push({

                        scheduleid:0,
                        startslot:scheduleSlots[i][j].startslot,
                        endslot:scheduleSlots[i][j].endslot,
                        dayofweek:scheduleSlots[i][j].dayofweek,
                        photographerid:0

                    })

                    

                }

            }
            
            slotList.push(temp)
        }

        if(!selectedAny){

            setErrorText("must select atleast 2")
            return

        }

        let newSlot = []

        for(let i = 0; i < slotList.length; i++){

            if(slotList[i].length === 0){

                continue

            }

            for(let j = 0; j < slotList[i].length; j++){

                const current = slotList[i][j].startslot

                const hasPrevious = j > 0 && current - slotList[i][j - 1].startslot === 1

                const hasNext = j < slotList[i].length - 1 && slotList[i][j + 1].startslot - current === 1

                if(!hasPrevious && !hasNext){
                    
                    setErrorText("Cannot select a single slot")
                    return
                }

            }

            let start = slotList[i][0].startslot
   

            for(let j = 1; j < slotList[i].length; j++){

                const isConsecutive =
                    slotList[i][j].startslot -
                    slotList[i][j - 1].startslot === 1

                const isLast = j === slotList[i].length - 1

                if(!isConsecutive){

                    newSlot.push({
                        slotid: 0,
                        startslot: start,
                        endslot: slotList[i][j - 1].endslot,
                        dayofweek: i,
                        photographerid: 0
                    })

                    start = slotList[i][j].startslot
                }

                if(isLast){

                    newSlot.push({
                        slotid: 0,
                        startslot: start,
                        endslot: slotList[i][j - 1].endslot,
                        dayofweek: i,
                        photographerid: 0
                    })
                }

            }

            const tempDate = new Date()

            if(newSlot[0].dayofweek === 6){

                tempDate.setDate(date.getDate() - 1)

            }else{

                tempDate.setDate(date.getDate() + (newSlot[0].dayofweek))

            }

            fetch("/api/booking/client/requestslot",{

                method:"POST",
                headers:{

                    "Content-Type":"application/json",
                    auth:localStorage.getItem("jwt")

                },
                body:JSON.stringify({

                    slotid: 0,
                    startslot: newSlot[0].startslot,
                    endslot: newSlot[0].endslot,
                    clientid: 0,
                    photographerid: photographerId,
                    status: "",
                    dateofshoot:tempDate

                })

            }).then(response => response.json()).then(async data => {

                if(!data.success){

                    if(data.internalCode === 401){

                        let result = await tokenService.requestJwt()
                        if(!result){

                            navigate("/rejectedjwt")

                        }
                        else{

                            fetch("/api/booking/client/requestslot",{

                                method:"POST",
                                headers:{

                                    "Content-Type":"application/json",
                                    auth:localStorage.getItem("jwt")

                                },
                                body:JSON.stringify({

                                    slotid: 0,
                                    startslot: newSlot[0].startslot,
                                    endslot: newSlot[0].endslot,
                                    clientid: 0,
                                    photographerid: photographerId,
                                    status: "",
                                    dateofshoot:tempDate

                                })

                            }).then(r => r.json()).then(d => {

                                if(d.success){

                                    setRetrievedBookings(d.data)

                                }
                                else{

                                    setErrorText(d.error)

                                }

                            })

                        }

                    }else{

                        setErrorText(data.error)

                    }

                }else{

                    setRetrievedBookings(data.data)

                }

            })

        }       

        console.log(newSlot)

        if(newSlot.length > 1){

            setErrorText("booking must not be split")
            return

        }

    }

    return (


        <div className="
        
            w-screen 
            min-h-screen 
            flex
            flex-col
            items-center
        
        ">
            <CHeader active={""}/>
            <div className="
            
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                w-[150vh]
                rounded-xl
                border-neutral-300
                h-[80vh]
                mt-10
                flex
                flex-col
                items-center
                


            ">
                <div className="
                
                    flex
                    flex-row
                    gap-3
                    mt-3

                ">
                    <button onClick={backWeek} className="
                    
                        text-neutral-500
                        hover:text-black
                        active:text-neutral-500
                    
                    ">Back</button>
                    <p className="
                    
                        text-neutral-700
                    
                    ">Week: {week}</p>
                    <button onClick={nextWeek} className="
                    
                        text-neutral-500
                        hover:text-black
                        active:text-neutral-500
                    
                    ">Next</button>
                </div>
                
                <div className="
                    

                    w-[130vh]
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    h-[60vh]
                    rounded-xl
                    mt-3
                    overflow-y-auto
                    overflow-x-hidden
                    p-2

                ">
                    
                    <div className="
                    
                        flex

                        justify-evenly  
                        border-t
                        border-b
                        border-r
                    
                    ">
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center

                        
                        ">
                            Time
                            <div className="
                            
                                border-t
                                w-10/10
                                flex
                                items-center
                                flex-col

                            
                            ">
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">00:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">00:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">01:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">01:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">02:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">02:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">03:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">03:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">04:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">04:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">05:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">05:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">06:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">06:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">07:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">07:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">08:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">08:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">09:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">09:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">10:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">10:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">11:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">11:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">12:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">12:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">13:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">13:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">14:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">14:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">15:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">15:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">16:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">16:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">17:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">17:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">18:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">18:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">19:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">19:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">20:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">20:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">21:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">21:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">22:00</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">22:30</p>
                                <p className="
                                
                                    border-b
                                    border-neutral-400
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">23:00</p>
                                <p className="
                                
                                    w-10/10
                                    flex
                                    items-center
                                    justify-center
                                    h-[3vh]
                                
                                ">23:30</p>
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Monday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[0]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}

                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Tuesday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[1]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Wednesday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[2]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Thursday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[3]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Friday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[4]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Saturday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[5]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                        <div className="
                        
                            border-l
                            flex-1
                            flex
                            flex-col
                            items-center
                        
                        ">
                            <p className="

                                border-b
                                w-10/10
                                text-center
                            
                            ">Sunday</p>
                            <div className="
                            
                                w-10/10
                            
                            ">
                                {scheduleSlots[6]?.map(slot => (

                                    <button onClick={() => {selectSlot(slot.status, slot.startslot, slot.dayofweek)}} className={`
                                    
                                        w-full
                                        block
                                        hover:shadow-[0_0px_5px_rgba(0,0,0,0.25)_inset]
                                        h-[3vh]
                                        ${slot.status === ""? "bg-neutral-100": ""}
                                        ${slot.status === "schedule"? "bg-blue-400": ""}
                                        ${slot.status === "selected"? "bg-amber-500": ""}
                                    
                                    `}></button>

                                ))}
                            </div>
                        </div>
                    </div>
                </div>
                    <p className="
                    
                        text-red-600
                        pt-10
                    
                    ">{errorText}</p>
                <div className="
                
                    flex
                    flex-row
                    gap-3
                    mt-10

                    xl:mt-5
                    xl:mb-5
                
                ">

                    <button onClick={submit} className="
                    
                        bg-black
                        text-white
                        rounded-xl
                        w-[10vh]
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
                    
                    ">Save</button>
                    <button onClick={revert} className="
                    
                        bg-red-600
                        text-white
                        rounded-xl
                        w-[10vh]
                        h-8.75
                        flex
                        items-center
                        justify-center
                         hover:bg-red-700
      
                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black
                    
                    ">Revert</button>
                    <button className="
                    
                        bg-black
                        text-white
                        rounded-xl
                        w-[10vh]
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
                    
                    ">Back</button>
                </div>

            </div>
        </div>

    )

}

export default MakeBooking